package gov.nist.javax.sip.message;

import gov.nist.javax.sip.header.Accept;
import gov.nist.javax.sip.header.AcceptEncoding;
import gov.nist.javax.sip.header.AcceptEncodingList;
import gov.nist.javax.sip.header.AcceptLanguage;
import gov.nist.javax.sip.header.AcceptLanguageList;
import gov.nist.javax.sip.header.AcceptList;
import gov.nist.javax.sip.header.AlertInfo;
import gov.nist.javax.sip.header.AlertInfoList;
import gov.nist.javax.sip.header.Allow;
import gov.nist.javax.sip.header.AllowList;
import gov.nist.javax.sip.header.Authorization;
import gov.nist.javax.sip.header.AuthorizationList;
import gov.nist.javax.sip.header.CallInfo;
import gov.nist.javax.sip.header.CallInfoList;
import gov.nist.javax.sip.header.Contact;
import gov.nist.javax.sip.header.ContactList;
import gov.nist.javax.sip.header.ContentEncoding;
import gov.nist.javax.sip.header.ContentEncodingList;
import gov.nist.javax.sip.header.ContentLanguage;
import gov.nist.javax.sip.header.ContentLanguageList;
import gov.nist.javax.sip.header.ErrorInfo;
import gov.nist.javax.sip.header.ErrorInfoList;
import gov.nist.javax.sip.header.ExtensionHeaderImpl;
import gov.nist.javax.sip.header.ExtensionHeaderList;
import gov.nist.javax.sip.header.InReplyTo;
import gov.nist.javax.sip.header.InReplyToList;
import gov.nist.javax.sip.header.ProxyAuthenticate;
import gov.nist.javax.sip.header.ProxyAuthenticateList;
import gov.nist.javax.sip.header.ProxyAuthorization;
import gov.nist.javax.sip.header.ProxyAuthorizationList;
import gov.nist.javax.sip.header.ProxyRequire;
import gov.nist.javax.sip.header.ProxyRequireList;
import gov.nist.javax.sip.header.RecordRoute;
import gov.nist.javax.sip.header.RecordRouteList;
import gov.nist.javax.sip.header.Require;
import gov.nist.javax.sip.header.RequireList;
import gov.nist.javax.sip.header.Route;
import gov.nist.javax.sip.header.RouteList;
import gov.nist.javax.sip.header.Supported;
import gov.nist.javax.sip.header.SupportedList;
import gov.nist.javax.sip.header.Unsupported;
import gov.nist.javax.sip.header.UnsupportedList;
import gov.nist.javax.sip.header.Via;
import gov.nist.javax.sip.header.ViaList;
import gov.nist.javax.sip.header.WWWAuthenticate;
import gov.nist.javax.sip.header.WWWAuthenticateList;
import gov.nist.javax.sip.header.Warning;
import gov.nist.javax.sip.header.WarningList;
import gov.nist.javax.sip.header.ims.PAssertedIdentity;
import gov.nist.javax.sip.header.ims.PAssertedIdentityList;
import gov.nist.javax.sip.header.ims.PAssociatedURI;
import gov.nist.javax.sip.header.ims.PAssociatedURIList;
import gov.nist.javax.sip.header.ims.PMediaAuthorization;
import gov.nist.javax.sip.header.ims.PMediaAuthorizationList;
import gov.nist.javax.sip.header.ims.PVisitedNetworkID;
import gov.nist.javax.sip.header.ims.PVisitedNetworkIDList;
import gov.nist.javax.sip.header.ims.Path;
import gov.nist.javax.sip.header.ims.PathList;
import gov.nist.javax.sip.header.ims.Privacy;
import gov.nist.javax.sip.header.ims.PrivacyList;
import gov.nist.javax.sip.header.ims.SecurityClient;
import gov.nist.javax.sip.header.ims.SecurityClientList;
import gov.nist.javax.sip.header.ims.SecurityServer;
import gov.nist.javax.sip.header.ims.SecurityServerList;
import gov.nist.javax.sip.header.ims.SecurityVerify;
import gov.nist.javax.sip.header.ims.SecurityVerifyList;
import gov.nist.javax.sip.header.ims.ServiceRoute;
import gov.nist.javax.sip.header.ims.ServiceRouteList;
import java.util.Hashtable;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public final class ListMap {
    public static Hashtable headerListTable;
    public static boolean initialized;

    static {
        initializeListMap();
    }

    public static void initializeListMap() {
        Hashtable hashtable = new Hashtable();
        headerListTable = hashtable;
        hashtable.put(ExtensionHeaderImpl.class, ExtensionHeaderList.class);
        headerListTable.put(Contact.class, ContactList.class);
        headerListTable.put(ContentEncoding.class, ContentEncodingList.class);
        headerListTable.put(Via.class, ViaList.class);
        headerListTable.put(WWWAuthenticate.class, WWWAuthenticateList.class);
        headerListTable.put(Accept.class, AcceptList.class);
        headerListTable.put(AcceptEncoding.class, AcceptEncodingList.class);
        headerListTable.put(AcceptLanguage.class, AcceptLanguageList.class);
        headerListTable.put(ProxyRequire.class, ProxyRequireList.class);
        headerListTable.put(Route.class, RouteList.class);
        headerListTable.put(Require.class, RequireList.class);
        headerListTable.put(Warning.class, WarningList.class);
        headerListTable.put(Unsupported.class, UnsupportedList.class);
        headerListTable.put(AlertInfo.class, AlertInfoList.class);
        headerListTable.put(CallInfo.class, CallInfoList.class);
        headerListTable.put(ProxyAuthenticate.class, ProxyAuthenticateList.class);
        headerListTable.put(ProxyAuthorization.class, ProxyAuthorizationList.class);
        headerListTable.put(Authorization.class, AuthorizationList.class);
        headerListTable.put(Allow.class, AllowList.class);
        headerListTable.put(RecordRoute.class, RecordRouteList.class);
        headerListTable.put(ContentLanguage.class, ContentLanguageList.class);
        headerListTable.put(ErrorInfo.class, ErrorInfoList.class);
        headerListTable.put(Supported.class, SupportedList.class);
        headerListTable.put(InReplyTo.class, InReplyToList.class);
        headerListTable.put(PAssociatedURI.class, PAssociatedURIList.class);
        headerListTable.put(PMediaAuthorization.class, PMediaAuthorizationList.class);
        headerListTable.put(Path.class, PathList.class);
        headerListTable.put(Privacy.class, PrivacyList.class);
        headerListTable.put(ServiceRoute.class, ServiceRouteList.class);
        headerListTable.put(PVisitedNetworkID.class, PVisitedNetworkIDList.class);
        headerListTable.put(SecurityClient.class, SecurityClientList.class);
        headerListTable.put(SecurityServer.class, SecurityServerList.class);
        headerListTable.put(SecurityVerify.class, SecurityVerifyList.class);
        headerListTable.put(PAssertedIdentity.class, PAssertedIdentityList.class);
        initialized = true;
    }
}
