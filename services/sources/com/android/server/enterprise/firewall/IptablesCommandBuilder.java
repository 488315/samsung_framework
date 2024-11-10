package com.android.server.enterprise.firewall;

import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.enterprise.firewall.FirewallDefinitions;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.samsung.android.knox.AppIdentity;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.net.firewall.DomainFilterRule;
import com.samsung.android.knox.net.firewall.Firewall;
import com.samsung.android.knox.net.firewall.FirewallRule;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class IptablesCommandBuilder {
    public static String getDomainFilterOnIptablesCheckCommand(boolean z) {
        return z ? "*filter\n:domainfilter-test -\nCOMMIT\n*filter\n-A domainfilter-test -m domainfilter --blacklist %testing% -j REJECT\nCOMMIT\n" : "*filter\n-D domainfilter-test -m domainfilter --blacklist %testing% -j REJECT\nCOMMIT\n*filter\n-X domainfilter-test\nCOMMIT\n";
    }

    /* JADX WARN: Removed duplicated region for block: B:109:0x04de  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x0222  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x01f1  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0274  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x05e6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.List createAllowOrDenyCommands(com.samsung.android.knox.net.firewall.FirewallRule r17, com.samsung.android.knox.ContextInfo r18, java.lang.String r19, com.samsung.android.knox.net.firewall.FirewallRule.RuleType r20) {
        /*
            Method dump skipped, instructions count: 1619
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.firewall.IptablesCommandBuilder.createAllowOrDenyCommands(com.samsung.android.knox.net.firewall.FirewallRule, com.samsung.android.knox.ContextInfo, java.lang.String, com.samsung.android.knox.net.firewall.FirewallRule$RuleType):java.util.List");
    }

    public static List createRedirectExceptionCommands(FirewallRule firewallRule, ContextInfo contextInfo, String str) {
        String str2;
        String str3;
        ArrayList arrayList = new ArrayList();
        String str4 = "";
        if ("*".equals(firewallRule.getIpAddress())) {
            str2 = "";
        } else if (firewallRule.getIpAddress().contains(PackageManagerShellCommandDataLoader.STDIN_PATH)) {
            str2 = "-m iprange --dst-range " + firewallRule.getIpAddress();
        } else {
            str2 = "-d " + firewallRule.getIpAddress();
        }
        if ("*".equals(firewallRule.getPortNumber())) {
            str3 = "";
        } else {
            String portNumber = firewallRule.getPortNumber();
            if (portNumber.contains(PackageManagerShellCommandDataLoader.STDIN_PATH)) {
                portNumber = portNumber.replace('-', ':');
            }
            str3 = " --dport " + portNumber;
        }
        if (!TextUtils.isEmpty(firewallRule.getStrNetworkInterface())) {
            str4 = " -o " + firewallRule.getStrNetworkInterface();
        } else if (!firewallRule.getNetworkInterface().equals(Firewall.NetworkInterface.ALL_NETWORKS)) {
            str4 = " -o " + FirewallUtils.convertNetworkInterfaceForIptables(firewallRule.getNetworkInterface());
        }
        int userId = UserHandle.getUserId(contextInfo.mCallerUid);
        String str5 = "firewall_exceptions-output";
        if (userId != 0) {
            str5 = "firewall_exceptions-output" + userId;
        }
        if (firewallRule.getProtocol().equals(Firewall.Protocol.TCP) || firewallRule.getProtocol().equals(Firewall.Protocol.ALL)) {
            arrayList.add(str + " " + str5 + " " + str2 + str4 + " -p tcp" + str3 + FirewallUtils.getAppOrUserUid(firewallRule, contextInfo) + " -j ACCEPT");
        }
        if (firewallRule.getProtocol().equals(Firewall.Protocol.UDP) || firewallRule.getProtocol().equals(Firewall.Protocol.ALL)) {
            arrayList.add(str + " " + str5 + " " + str2 + str4 + " -p udp" + str3 + FirewallUtils.getAppOrUserUid(firewallRule, contextInfo) + " -j ACCEPT");
        }
        return arrayList;
    }

    public static List createRedirectCommands(FirewallRule firewallRule, ContextInfo contextInfo, String str) {
        String str2;
        String str3;
        ArrayList arrayList = new ArrayList();
        String str4 = "";
        if ("*".equals(firewallRule.getIpAddress())) {
            str2 = "";
        } else if (firewallRule.getIpAddress().contains(PackageManagerShellCommandDataLoader.STDIN_PATH)) {
            str2 = "-m iprange --dst-range " + firewallRule.getIpAddress();
        } else {
            str2 = "-d " + firewallRule.getIpAddress();
        }
        if ("*".equals(firewallRule.getPortNumber())) {
            str3 = "";
        } else {
            String portNumber = firewallRule.getPortNumber();
            if (portNumber.contains(PackageManagerShellCommandDataLoader.STDIN_PATH)) {
                portNumber = portNumber.replace('-', ':');
            }
            str3 = " --dport " + portNumber + " ";
        }
        if (!TextUtils.isEmpty(firewallRule.getStrNetworkInterface())) {
            str4 = " -o " + firewallRule.getStrNetworkInterface();
        } else if (!firewallRule.getNetworkInterface().equals(Firewall.NetworkInterface.ALL_NETWORKS)) {
            str4 = " -o " + FirewallUtils.convertNetworkInterfaceForIptables(firewallRule.getNetworkInterface());
        }
        int userId = UserHandle.getUserId(contextInfo.mCallerUid);
        String str5 = "firewall_redirect-output";
        if (userId != 0) {
            str5 = "firewall_redirect-output" + userId;
        }
        if (firewallRule.getProtocol().equals(Firewall.Protocol.TCP) || firewallRule.getProtocol().equals(Firewall.Protocol.ALL)) {
            String str6 = str + " " + str5 + " " + str2 + str4 + " -p tcp" + str3 + FirewallUtils.getAppOrUserUid(firewallRule, contextInfo) + " -j DNAT --to-destination ";
            if (firewallRule.getAddressType().equals(Firewall.AddressType.IPV6)) {
                arrayList.add(str6 + "[" + firewallRule.getTargetIpAddress() + "]:" + firewallRule.getTargetPortNumber());
            } else {
                arrayList.add(str6 + firewallRule.getTargetIpAddress() + XmlUtils.STRING_ARRAY_SEPARATOR + firewallRule.getTargetPortNumber());
            }
        }
        if (firewallRule.getProtocol().equals(Firewall.Protocol.UDP) || firewallRule.getProtocol().equals(Firewall.Protocol.ALL)) {
            String str7 = str + " " + str5 + " " + str2 + str4 + " -p udp" + str3 + FirewallUtils.getAppOrUserUid(firewallRule, contextInfo) + " -j DNAT --to-destination ";
            if (firewallRule.getAddressType().equals(Firewall.AddressType.IPV6)) {
                arrayList.add(str7 + "[" + firewallRule.getTargetIpAddress() + "]:" + firewallRule.getTargetPortNumber());
            } else {
                arrayList.add(str7 + firewallRule.getTargetIpAddress() + XmlUtils.STRING_ARRAY_SEPARATOR + firewallRule.getTargetPortNumber());
            }
        }
        return arrayList;
    }

    public static List getCreateDenyPort53Commands(FirewallRule firewallRule, ContextInfo contextInfo, String str, FirewallRule.RuleType ruleType) {
        List createAllowOrDenyCommands = createAllowOrDenyCommands(firewallRule, contextInfo, str, ruleType);
        ArrayList arrayList = new ArrayList();
        Iterator it = createAllowOrDenyCommands.iterator();
        while (it.hasNext()) {
            arrayList.add(((String) it.next()).replaceAll("firewall_deny-output", "block_port53-output"));
        }
        return arrayList;
    }

    public static List getCreateFilterChains(List list) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(KnoxVpnFirewallHelper.TABLE_FILTER);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            String num2 = num.intValue() == 0 ? "" : num.toString();
            if ("".equals(num2)) {
                arrayList.add(":domain_filter-input -");
                arrayList.add(":firewall_allow-input -");
                arrayList.add(":firewall_deny-input -");
                arrayList.add(":firewall_allow-forward -");
                arrayList.add(":firewall_deny-forward -");
            }
            arrayList.add(":domain_filter-output" + num2 + " -");
            arrayList.add(":firewall_allow-output" + num2 + " -");
            arrayList.add(":firewall_deny-output" + num2 + " -");
            arrayList.add(":block_port53-output" + num2 + " -");
            StringBuilder sb = new StringBuilder();
            sb.append("-A OUTPUT -j domain_filter-output");
            sb.append(num2);
            arrayList.add(sb.toString());
            arrayList.add("-A OUTPUT -j firewall_allow-output" + num2);
            arrayList.add("-A OUTPUT -j firewall_deny-output" + num2);
            arrayList.add("-A OUTPUT -j block_port53-output" + num2);
            if ("".equals(num2)) {
                arrayList.add("-A INPUT -j domain_filter-input");
                arrayList.add("-A INPUT -j firewall_allow-input");
                arrayList.add("-A INPUT -j firewall_deny-input");
                arrayList.add("-A FORWARD -j firewall_allow-forward");
                arrayList.add("-A FORWARD -j firewall_deny-forward");
            }
        }
        arrayList.add(KnoxVpnFirewallHelper.COMMIT_CMD);
        return arrayList;
    }

    public static List getCreateNatChains(List list) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(KnoxVpnFirewallHelper.TABLE_NAT);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            String num2 = num.intValue() == 0 ? "" : num.toString();
            arrayList.add(":firewall_exceptions-output" + num2 + " -");
            arrayList.add(":firewall_redirect-output" + num2 + " -");
            StringBuilder sb = new StringBuilder();
            sb.append("-A OUTPUT -j firewall_exceptions-output");
            sb.append(num2);
            arrayList.add(sb.toString());
            arrayList.add("-A OUTPUT -j firewall_redirect-output" + num2);
        }
        arrayList.add(KnoxVpnFirewallHelper.COMMIT_CMD);
        return arrayList;
    }

    public static List getCreateDomainFilterChainsForUidCommands(String str, int i, int i2, boolean z) {
        String str2;
        ArrayList arrayList = new ArrayList();
        String domainFilterChainNameForApp = getDomainFilterChainNameForApp(str, Integer.valueOf(i), Integer.valueOf(i2), true);
        String domainFilterChainNameForApp2 = getDomainFilterChainNameForApp(str, Integer.valueOf(i), Integer.valueOf(i2), false);
        String domainFilterBaseChainNameForUser = getDomainFilterBaseChainNameForUser(i2, true);
        String domainFilterBaseChainNameForUser2 = getDomainFilterBaseChainNameForUser(i2, false);
        String appOrUserUid = FirewallUtils.getAppOrUserUid(str, i2, true);
        arrayList.add(KnoxVpnFirewallHelper.TABLE_FILTER);
        if (z) {
            arrayList.add(XmlUtils.STRING_ARRAY_SEPARATOR + domainFilterChainNameForApp + " -");
        }
        arrayList.add(XmlUtils.STRING_ARRAY_SEPARATOR + domainFilterChainNameForApp2 + " -");
        if ("*".equals(str)) {
            str2 = "-A";
        } else {
            StringBuilder sb = new StringBuilder();
            str2 = "-I";
            sb.append("-I");
            sb.append(" ");
            sb.append(domainFilterBaseChainNameForUser2);
            sb.append(appOrUserUid);
            sb.append(" -j RETURN ");
            arrayList.add(sb.toString());
        }
        if (z) {
            arrayList.add(str2 + " " + domainFilterBaseChainNameForUser + " -j " + domainFilterChainNameForApp);
        }
        arrayList.add(str2 + " " + domainFilterBaseChainNameForUser2 + appOrUserUid + " -j " + domainFilterChainNameForApp2);
        arrayList.add(KnoxVpnFirewallHelper.COMMIT_CMD);
        return arrayList;
    }

    public static List getRemoveDomainFilterChainsForUidCommands(String str, int i, int i2, boolean z) {
        String str2;
        ArrayList arrayList = new ArrayList();
        String domainFilterChainNameForApp = getDomainFilterChainNameForApp(str, Integer.valueOf(i), Integer.valueOf(i2), true);
        String domainFilterChainNameForApp2 = getDomainFilterChainNameForApp(str, Integer.valueOf(i), Integer.valueOf(i2), false);
        String domainFilterBaseChainNameForUser = getDomainFilterBaseChainNameForUser(i2, true);
        String domainFilterBaseChainNameForUser2 = getDomainFilterBaseChainNameForUser(i2, false);
        if (i == -1) {
            str2 = FirewallUtils.getAppOrUserUid("*", i2, true);
        } else {
            str2 = " -m owner --uid-owner " + Integer.toString(i);
        }
        arrayList.add(KnoxVpnFirewallHelper.TABLE_FILTER);
        if (z) {
            arrayList.add(XmlUtils.STRING_ARRAY_SEPARATOR + domainFilterChainNameForApp + " -");
        }
        arrayList.add(XmlUtils.STRING_ARRAY_SEPARATOR + domainFilterChainNameForApp2 + " -");
        if (z) {
            arrayList.add("-D " + domainFilterBaseChainNameForUser + " -j " + domainFilterChainNameForApp);
        }
        arrayList.add("-D " + domainFilterBaseChainNameForUser2 + str2 + " -j " + domainFilterChainNameForApp2);
        if (i != -1 && !"*".equals(str)) {
            arrayList.add("-D " + domainFilterBaseChainNameForUser2 + str2 + " -j RETURN ");
        }
        if (z) {
            arrayList.add("-X " + domainFilterChainNameForApp);
        }
        arrayList.add("-X " + domainFilterChainNameForApp2);
        arrayList.add(KnoxVpnFirewallHelper.COMMIT_CMD);
        return arrayList;
    }

    public static String getDomainFilterChainNameForApp(String str, Integer num, Integer num2, boolean z) {
        if (z) {
            return "domain_input-all";
        }
        if ((str != null && "*".equals(str)) || (num != null && num.intValue() == -1)) {
            return "domain_output-all" + (num2.intValue() != 0 ? Integer.toString(num2.intValue()) : "");
        }
        if (num == null) {
            return "";
        }
        return "domain_output-uid" + Integer.toString(num.intValue());
    }

    public static String getDomainFilterBaseChainNameForUser(int i, boolean z) {
        String num = i == 0 ? "" : Integer.toString(i);
        if (z) {
            return "domain_filter-input";
        }
        return "domain_filter-output" + num;
    }

    public static String getBlockDnsPortBaseChainNameForUser(int i) {
        return "block_port53-output" + (i == 0 ? "" : Integer.toString(i));
    }

    public static List getDomainFlushBaseChainsCommand(Integer num, boolean z, boolean z2) {
        ArrayList arrayList = new ArrayList();
        String domainFilterBaseChainNameForUser = getDomainFilterBaseChainNameForUser(num.intValue(), true);
        String domainFilterBaseChainNameForUser2 = getDomainFilterBaseChainNameForUser(num.intValue(), false);
        if (z2) {
            arrayList.add(XmlUtils.STRING_ARRAY_SEPARATOR + domainFilterBaseChainNameForUser + " -");
        }
        arrayList.add(XmlUtils.STRING_ARRAY_SEPARATOR + domainFilterBaseChainNameForUser2 + " -");
        if (num.intValue() != 0 && z) {
            if (z2) {
                arrayList.add("-D INPUT -j " + domainFilterBaseChainNameForUser);
            }
            arrayList.add("-D OUTPUT -j " + domainFilterBaseChainNameForUser2);
            if (z2) {
                arrayList.add("-X " + domainFilterBaseChainNameForUser);
            }
            arrayList.add("-X " + domainFilterBaseChainNameForUser2);
        }
        return arrayList;
    }

    public static List getDnsPortFlushBaseChainsCommand(Integer num, boolean z) {
        ArrayList arrayList = new ArrayList();
        String blockDnsPortBaseChainNameForUser = getBlockDnsPortBaseChainNameForUser(num.intValue());
        arrayList.add(XmlUtils.STRING_ARRAY_SEPARATOR + blockDnsPortBaseChainNameForUser + " -");
        if (num.intValue() != 0 && z) {
            arrayList.add("-D OUTPUT -j " + blockDnsPortBaseChainNameForUser);
            arrayList.add("-X " + blockDnsPortBaseChainNameForUser);
        }
        return arrayList;
    }

    public static List getRemoveFilterChainsCommand(Integer num) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(KnoxVpnFirewallHelper.TABLE_FILTER);
        if (num.intValue() == 0) {
            arrayList.add("-D INPUT -j firewall_allow-input");
            arrayList.add("-D INPUT -j firewall_deny-input");
            arrayList.add("-D FORWARD -j firewall_allow-forward");
            arrayList.add("-D FORWARD -j firewall_deny-forward");
        }
        arrayList.add("-D OUTPUT -j firewall_allow-output" + num);
        arrayList.add("-D OUTPUT -j firewall_deny-output" + num);
        if (num.intValue() == 0) {
            arrayList.add(":firewall_allow-input -");
            arrayList.add(":firewall_deny-input -");
            arrayList.add(":firewall_allow-forward -");
            arrayList.add(":firewall_deny-forward -");
        }
        arrayList.add(":firewall_allow-output" + num + " -");
        arrayList.add(":firewall_deny-output" + num + " -");
        if (num.intValue() == 0) {
            arrayList.add("-X firewall_allow-input");
            arrayList.add("-X firewall_deny-input");
            arrayList.add("-X firewall_allow-forward");
            arrayList.add("-X firewall_deny-forward");
        }
        arrayList.add("-X firewall_allow-output" + num);
        arrayList.add("-X firewall_deny-output" + num);
        arrayList.add(KnoxVpnFirewallHelper.COMMIT_CMD);
        return arrayList;
    }

    public static List getRemoveNatChainsCommand(Integer num) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(KnoxVpnFirewallHelper.TABLE_NAT);
        arrayList.add("-D OUTPUT -j firewall_exceptions-output" + num);
        arrayList.add("-D OUTPUT -j firewall_redirect-output" + num);
        arrayList.add(":firewall_exceptions-output" + num + " -");
        arrayList.add(":firewall_redirect-output" + num + " -");
        StringBuilder sb = new StringBuilder();
        sb.append("-X firewall_exceptions-output");
        sb.append(num);
        arrayList.add(sb.toString());
        arrayList.add("-X firewall_redirect-output" + num);
        arrayList.add(KnoxVpnFirewallHelper.COMMIT_CMD);
        return arrayList;
    }

    public static List getFlushChainsCommand(FirewallRule.RuleType ruleType, Integer num) {
        ArrayList arrayList = new ArrayList();
        String num2 = num.intValue() == 0 ? "" : num.toString();
        int i = AnonymousClass1.$SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType[ruleType.ordinal()];
        if (i == 1) {
            arrayList.add(KnoxVpnFirewallHelper.TABLE_FILTER);
            arrayList.add(":firewall_allow-input -");
            arrayList.add(":firewall_allow-output" + num2 + " -");
            arrayList.add(":firewall_allow-forward -");
        } else if (i == 2) {
            arrayList.add(KnoxVpnFirewallHelper.TABLE_FILTER);
            arrayList.add(":firewall_deny-input -");
            arrayList.add(":firewall_deny-output" + num2 + " -");
            arrayList.add(":firewall_deny-forward -");
        } else if (i == 3) {
            arrayList.add(KnoxVpnFirewallHelper.TABLE_NAT);
            arrayList.add(":firewall_redirect-output" + num2 + " -");
        } else if (i == 4) {
            arrayList.add(KnoxVpnFirewallHelper.TABLE_NAT);
            arrayList.add(":firewall_exceptions-output" + num2 + " -");
        }
        arrayList.add(KnoxVpnFirewallHelper.COMMIT_CMD);
        return arrayList;
    }

    public static List getIptablesCommand(FirewallRule firewallRule, ContextInfo contextInfo, String str) {
        int i = AnonymousClass1.$SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType[firewallRule.getRuleType().ordinal()];
        if (i == 1) {
            return createAllowOrDenyCommands(firewallRule, contextInfo, str, FirewallRule.RuleType.ALLOW);
        }
        if (i == 2) {
            return createAllowOrDenyCommands(firewallRule, contextInfo, str, FirewallRule.RuleType.DENY);
        }
        if (i == 3) {
            return createRedirectCommands(firewallRule, contextInfo, str);
        }
        if (i != 4) {
            return null;
        }
        return createRedirectExceptionCommands(firewallRule, contextInfo, str);
    }

    public static List getListIptablesRestoreCommand(String str) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("*" + str + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        arrayList.add("-L -n -v --line-numbers");
        arrayList.add(KnoxVpnFirewallHelper.COMMIT_CMD);
        return arrayList;
    }

    public static List getDomainRemoveIptablesCommand(DomainFilterRule domainFilterRule, ContextInfo contextInfo, boolean z) {
        return createAppendDeleteDomainCommands(domainFilterRule, "-D", contextInfo, z);
    }

    public static List getDomainIptablesCommand(DomainFilterRule domainFilterRule, ContextInfo contextInfo, boolean z) {
        return createAppendDeleteDomainCommands(domainFilterRule, "-A", contextInfo, z);
    }

    public static List createAppendDeleteDomainCommands(DomainFilterRule domainFilterRule, String str, ContextInfo contextInfo, boolean z) {
        Integer uidForApplication;
        ArrayList arrayList = new ArrayList();
        String packageName = domainFilterRule.getApplication().getPackageName();
        int userId = UserHandle.getUserId(contextInfo.mCallerUid);
        if ("*".equals(packageName)) {
            uidForApplication = Integer.valueOf(userId);
        } else {
            uidForApplication = FirewallUtils.getUidForApplication(packageName, userId);
            if (uidForApplication == null) {
                Log.e("FirewallCommandBuilder", "Failed to get uid for " + packageName);
                return arrayList;
            }
        }
        String domainFilterChainNameForApp = getDomainFilterChainNameForApp(packageName, uidForApplication, Integer.valueOf(userId), true);
        String domainFilterChainNameForApp2 = getDomainFilterChainNameForApp(packageName, uidForApplication, Integer.valueOf(userId), false);
        String str2 = "-A".equals(str) ? "-I" : str;
        for (String str3 : domainFilterRule.getAllowDomains()) {
            if (str3.contains("*")) {
                str3 = str3.replaceAll("\\*", "%");
            }
            arrayList.add(str2 + " " + domainFilterChainNameForApp2 + " -m domainfilter --whitelist " + str3 + " -j ACCEPT");
        }
        for (String str4 : domainFilterRule.getDenyDomains()) {
            if (str4.contains("*")) {
                str4 = str4.replaceAll("\\*", "%");
            }
            arrayList.add(str + " " + domainFilterChainNameForApp2 + " -m domainfilter --blacklist " + str4 + " -j REJECT");
        }
        if (z) {
            arrayList.add(str2 + " " + domainFilterChainNameForApp + " -j ACCEPT");
        }
        return arrayList;
    }

    /* renamed from: com.android.server.enterprise.firewall.IptablesCommandBuilder$1, reason: invalid class name */
    /* loaded from: classes2.dex */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$server$enterprise$firewall$FirewallDefinitions$Table;
        public static final /* synthetic */ int[] $SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType;

        static {
            int[] iArr = new int[FirewallDefinitions.Table.values().length];
            $SwitchMap$com$android$server$enterprise$firewall$FirewallDefinitions$Table = iArr;
            try {
                iArr[FirewallDefinitions.Table.FILTER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$server$enterprise$firewall$FirewallDefinitions$Table[FirewallDefinitions.Table.NAT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            int[] iArr2 = new int[FirewallRule.RuleType.values().length];
            $SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType = iArr2;
            try {
                iArr2[FirewallRule.RuleType.ALLOW.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType[FirewallRule.RuleType.DENY.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType[FirewallRule.RuleType.REDIRECT.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType[FirewallRule.RuleType.REDIRECT_EXCEPTION.ordinal()] = 4;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public static String getTestIpv6Commands(FirewallDefinitions.Table table) {
        StringBuilder sb = new StringBuilder();
        int i = AnonymousClass1.$SwitchMap$com$android$server$enterprise$firewall$FirewallDefinitions$Table[table.ordinal()];
        if (i == 1) {
            sb.append(KnoxVpnFirewallHelper.TABLE_FILTER);
            sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        } else if (i == 2) {
            sb.append(KnoxVpnFirewallHelper.TABLE_NAT);
            sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        }
        sb.append(":test_ipv6 -\n");
        sb.append("-X test_ipv6\n");
        sb.append(KnoxVpnFirewallHelper.COMMIT_CMD);
        return sb.toString();
    }

    public static List getFlushFilterChainsCommand(Integer num) {
        ArrayList arrayList = new ArrayList();
        String str = "";
        if (num != null && num.intValue() != 0) {
            str = num.toString();
        }
        arrayList.add(KnoxVpnFirewallHelper.TABLE_FILTER);
        arrayList.add(":firewall_allow-input -");
        arrayList.add(":firewall_allow-output" + str + " -");
        arrayList.add(":firewall_allow-forward -");
        arrayList.add(":firewall_deny-input -");
        arrayList.add(":firewall_deny-output" + str + " -");
        arrayList.add(":firewall_deny-forward -");
        arrayList.add(KnoxVpnFirewallHelper.COMMIT_CMD);
        return arrayList;
    }

    public static List getFlushNatChainsCommand(Integer num) {
        ArrayList arrayList = new ArrayList();
        String str = "";
        if (num != null && num.intValue() != 0) {
            str = num.toString();
        }
        arrayList.add(KnoxVpnFirewallHelper.TABLE_NAT);
        arrayList.add(":firewall_exceptions-output" + str + " -");
        arrayList.add(":firewall_redirect-output" + str + " -");
        arrayList.add(KnoxVpnFirewallHelper.COMMIT_CMD);
        return arrayList;
    }

    public static List createExemptRulesCommands(boolean z, ContextInfo contextInfo) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(KnoxVpnFirewallHelper.TABLE_FILTER);
        String str = z ? "-A" : "-D";
        FirewallRule firewallRule = new FirewallRule(FirewallRule.RuleType.ALLOW, Firewall.AddressType.IPV4);
        firewallRule.setDirection(Firewall.Direction.OUTPUT);
        Iterator it = FirewallDefinitions.EXEMPT_PACKAGE_LIST.keySet().iterator();
        while (it.hasNext()) {
            firewallRule.setApplication(new AppIdentity((String) it.next(), (String) null));
            arrayList.addAll(createAllowOrDenyCommands(firewallRule, contextInfo, str, FirewallRule.RuleType.ALLOW));
        }
        arrayList.add(KnoxVpnFirewallHelper.COMMIT_CMD);
        return arrayList;
    }

    public static List createKGExemptRuleCommand(boolean z, ContextInfo contextInfo) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(KnoxVpnFirewallHelper.TABLE_FILTER);
        String str = z ? "-A" : "-D";
        FirewallRule firewallRule = new FirewallRule(FirewallRule.RuleType.ALLOW, Firewall.AddressType.IPV4);
        firewallRule.setDirection(Firewall.Direction.OUTPUT);
        firewallRule.setApplication(new AppIdentity(KnoxCustomManagerService.KG_PKG_NAME, (String) null));
        arrayList.addAll(createAllowOrDenyCommands(firewallRule, contextInfo, str, FirewallRule.RuleType.ALLOW));
        arrayList.add(KnoxVpnFirewallHelper.COMMIT_CMD);
        return arrayList;
    }

    public static List createExemptRulesCommandsForDnsTether(boolean z, ContextInfo contextInfo) {
        ArrayList arrayList = new ArrayList();
        String str = z ? "-A" : "-D";
        FirewallRule firewallRule = new FirewallRule(FirewallRule.RuleType.ALLOW, Firewall.AddressType.IPV4);
        firewallRule.setDirection(Firewall.Direction.OUTPUT);
        firewallRule.setApplication(new AppIdentity("dns_tether", (String) null));
        Iterator it = createAllowOrDenyCommands(firewallRule, contextInfo, str, FirewallRule.RuleType.ALLOW).iterator();
        while (it.hasNext()) {
            arrayList.add(((String) it.next()).replaceAll("firewall_allow-output", "block_port53-output"));
        }
        return arrayList;
    }

    public static List createIcmpAllowRuleCommands(boolean z, int i) {
        String str = z ? "-I" : "-D";
        ArrayList arrayList = new ArrayList();
        arrayList.add(KnoxVpnFirewallHelper.TABLE_FILTER);
        StringBuilder sb = new StringBuilder();
        if (i == 4) {
            sb.append(str + " firewall_allow-input -p icmp -m icmp --icmp-type 3 -m state --state RELATED,ESTABLISHED -j ACCEPT");
        } else if (i == 6) {
            sb.append(str + " firewall_allow-input -p icmpv6 -m icmpv6 --icmpv6-type 1 -m state --state RELATED,ESTABLISHED -j ACCEPT");
        }
        arrayList.add(sb.toString());
        arrayList.add(KnoxVpnFirewallHelper.COMMIT_CMD);
        return arrayList;
    }

    public static List getCreateDomainFilterExceptionUidRules(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("-I " + getDomainFilterChainNameForApp("*", Integer.valueOf(i), Integer.valueOf(UserHandle.getUserId(i)), false) + " -m owner --uid-owner " + i + " -j ACCEPT ");
        return arrayList;
    }

    public static List getCreateDomainFilterExceptionForSpecificUidRule(String str, int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("-I " + getDomainFilterChainNameForApp(str, Integer.valueOf(i), Integer.valueOf(UserHandle.getUserId(i)), false) + " -m owner --uid-owner " + i + " -j ACCEPT ");
        return arrayList;
    }
}
