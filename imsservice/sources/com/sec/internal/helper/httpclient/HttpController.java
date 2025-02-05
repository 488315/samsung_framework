package com.sec.internal.helper.httpclient;

import android.text.TextUtils;
import android.webkit.URLUtil;
import com.sec.internal.ims.cmstore.helper.CircularQueue;
import com.sec.internal.ims.cmstore.omanetapi.nms.CloudMessageGetAllPayloads;
import com.sec.internal.ims.cmstore.omanetapi.nms.CloudMessageGetIndividualPayLoad;
import com.sec.internal.ims.cmstore.omanetapi.nms.CloudMessageGetLargeFile;
import com.sec.internal.ims.gba.GbaUtility;
import com.sec.internal.ims.util.ImsUtil;
import com.sec.internal.imscr.LogClass;
import com.sec.internal.log.IMSLog;
import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;
import okhttp3.internal.tls.OkHostnameVerifier;

/* loaded from: classes.dex */
public class HttpController {
    public static final int CAPACITY = 100;
    public static final String CHARSET_UTF8 = "UTF-8";
    public static final String CLASS_NAME_PREFIX = "CloudMessage";
    public static final String CONTENT_TYPE_CAB_XML = "application/vnd.oma.cab-address-book+xml";
    public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String CONTENT_TYPE_XCAP_EL_XML = "application/xcap-el+xml";
    public static final String CONTENT_TYPE_XML = "application/xml";
    public static final String ENCODING_GZIP = "gzip";
    public static final String HEADER_ACCEPT = "Accept";
    public static final String HEADER_ACCEPT_CHARSET = "Accept-Charset";
    public static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
    public static final String HEADER_AUTHENTICATION_INFO = "Authentication-Info";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String HEADER_CACHE_CONTROL = "Cache-Control";
    public static final String HEADER_CONTENT_DISPOSITION = "Content-Disposition";
    public static final String HEADER_CONTENT_ENCODING = "Content-Encoding";
    public static final String HEADER_CONTENT_ID = "Content-ID";
    public static final String HEADER_CONTENT_LENGTH = "Content-Length";
    public static final String HEADER_CONTENT_RANGE = "Content-Range";
    public static final String HEADER_CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_COOKIE = "Cookie";
    public static final String HEADER_DATE = "Date";
    public static final String HEADER_DEVICE_AGENT = "Device-Agent";
    public static final String HEADER_ETAG = "ETag";
    public static final String HEADER_EXPIRES = "Expires";
    public static final String HEADER_FILE_ICON = "File-Icon";
    public static final String HEADER_HOST = "Host";
    public static final String HEADER_IF_NONE_MATCH = "If-None-Match";
    public static final String HEADER_IIDTOKEN = "gmscore_instance_id_token";
    public static final String HEADER_LAST_MODIFIED = "Last-Modified";
    public static final String HEADER_LOCATION = "Location";
    public static final String HEADER_PROXY_AUTHORIZATION = "Proxy-Authorization";
    public static final String HEADER_RANGE = "Range";
    public static final String HEADER_REFERER = "Referer";
    public static final String HEADER_SERVER = "Server";
    public static final String HEADER_SET_COOKIE = "Set-Cookie";
    public static final String HEADER_USER_AGENT = "User-Agent";
    public static final String HEADER_WWW_AUTHENTICATE = "WWW-Authenticate";
    public static final String HEADER_X_TMUS_IMEI = "X-TMUS-IMEI";
    public static final String METHOD_DELETE = "DELETE";
    public static final String METHOD_GET = "GET";
    public static final String METHOD_HEAD = "HEAD";
    public static final String METHOD_OPTIONS = "OPTIONS";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_TRACE = "TRACE";
    public static final int OTHERTYPE_CIPHERSUITE = 1;
    public static final String PARAM_CHARSET = "charset";
    private static final String TAG = "HttpController";
    public static final String VAL_3GPP_GBA = "3gpp-gba";
    private static boolean isInitialized = false;
    private final int API_SIGNATURE_MAX_INT = 100000;
    public String req = "";
    public static List<CircularQueue<String>> queue_sim = new ArrayList(2);
    private static volatile HttpController sInstance = new HttpController();
    private static volatile Random random = new Random();
    protected static boolean mIsDebugHttps = false;
    private static volatile List<ConnectionSpec> mConnectionSpecs = null;
    private static volatile HostnameVerifier mHostnameVerifier = null;
    protected static final OkHttpClient sOkHttpClient = new OkHttpClient();

    protected HttpController() {
    }

    public static HttpController getInstance() {
        return sInstance;
    }

    protected static ConnectionSpec configConnectionSpecForSpecificOperator() {
        return new ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS).cipherSuites(CipherSuite.TLS_AES_128_GCM_SHA256, CipherSuite.TLS_AES_256_GCM_SHA384, CipherSuite.TLS_CHACHA20_POLY1305_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_DHE_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_RSA_WITH_3DES_EDE_CBC_SHA).build();
    }

    protected static ConnectionSpec configConnectionSpec() {
        return new ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS).cipherSuites(CipherSuite.TLS_AES_128_GCM_SHA256, CipherSuite.TLS_AES_256_GCM_SHA384, CipherSuite.TLS_CHACHA20_POLY1305_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_DHE_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_RSA_WITH_3DES_EDE_CBC_SHA).build();
    }

    protected static TrustManager[] getTrustAllCertMangers() {
        return new TrustManager[]{new X509TrustManager() { // from class: com.sec.internal.helper.httpclient.HttpController.1
            @Override // javax.net.ssl.X509TrustManager
            public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) {
            }

            @Override // javax.net.ssl.X509TrustManager
            public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) {
            }

            @Override // javax.net.ssl.X509TrustManager
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }};
    }

    protected static SSLSocketFactory createSslSocketFactory(TrustManager[] trustManagerArr) throws Exception {
        SSLContext sSLContext = SSLContext.getInstance("TLS");
        sSLContext.init(null, trustManagerArr, ImsUtil.getRandom());
        return sSLContext.getSocketFactory();
    }

    public static boolean isIsInitialized() {
        return isInitialized;
    }

    public static void setIsInitialized(boolean z) {
        isInitialized = z;
    }

    public void initializeQueue_sim() {
        for (int i = 0; i < 2; i++) {
            queue_sim.add(i, new CircularQueue<>(100));
        }
        setIsInitialized(true);
    }

    public void setDebugHttps(boolean z) {
        mIsDebugHttps = z;
    }

    public void execute(final HttpRequestParams httpRequestParams) {
        if (isValidRequestParam(httpRequestParams, true)) {
            Call call = getCall(httpRequestParams);
            if (call == null) {
                httpRequestParams.getCallback().onFail(new IOException("okhttp fail to create call"));
                return;
            }
            if (!isIsInitialized()) {
                initializeQueue_sim();
            }
            final String generateRandomString = generateRandomString(100000);
            final String simpleName = httpRequestParams.getClass().getSimpleName();
            String str = TAG;
            IMSLog.i(str, "HTTP Request " + generateRandomString + " " + simpleName);
            IMSLog.i(str, "HTTP Request " + generateRandomString + " " + httpRequestParams);
            listToDumpFormat(LogClass.MCS_HTTP_REQUEST, httpRequestParams, generateRandomString, null);
            this.req += new Timestamp(System.currentTimeMillis()).toString() + "  HttpRequestParams " + simpleName + " [    " + httpRequestParams.getMethod().name() + "   nUrl: " + IMSLog.numberChecker(httpRequestParams.getUrl()) + "   bUrl: " + IMSLog.numberChecker(httpRequestParams.getBsfUrl()) + " ] ";
            try {
                call.enqueue(new Callback() { // from class: com.sec.internal.helper.httpclient.HttpController.2
                    @Override // okhttp3.Callback
                    public void onResponse(Call call2, Response response) {
                        String stringWoPayload;
                        HttpResponseParams buildResponse = HttpResponseBuilder.buildResponse(response);
                        if (buildResponse == null) {
                            httpRequestParams.getCallback().onFail(new IOException("okhttp response build failure"));
                            return;
                        }
                        if (httpRequestParams.getUseTls() && response.handshake() != null) {
                            buildResponse.setCipherSuite(GbaUtility.convertCipherSuite(response.handshake().cipherSuite().toString(), false));
                        }
                        if (CloudMessageGetAllPayloads.class.getSimpleName().equalsIgnoreCase(simpleName) || CloudMessageGetIndividualPayLoad.class.getSimpleName().equalsIgnoreCase(simpleName) || CloudMessageGetLargeFile.class.getSimpleName().equalsIgnoreCase(simpleName)) {
                            stringWoPayload = buildResponse.toStringWoPayload();
                        } else {
                            stringWoPayload = buildResponse.toString();
                        }
                        IMSLog.i(HttpController.TAG, "HTTP response: " + generateRandomString + " " + simpleName + " " + stringWoPayload);
                        StringBuilder sb = new StringBuilder();
                        HttpController httpController = HttpController.this;
                        sb.append(httpController.req);
                        sb.append(" RESPONSE [ code ");
                        sb.append(buildResponse.getStatusCode());
                        sb.append(" ] ");
                        httpController.req = sb.toString();
                        HttpController.listToDumpFormat(LogClass.MCS_HTTP_RESPONSE, httpRequestParams, generateRandomString, Integer.toString(buildResponse.getStatusCode()));
                        if (httpRequestParams.getPhoneId() != -1 && httpRequestParams.getPhoneId() < 2) {
                            HttpController.queue_sim.get(httpRequestParams.getPhoneId()).add(HttpController.this.req);
                        }
                        HttpController.this.req = "";
                        httpRequestParams.getCallback().onComplete(buildResponse);
                    }

                    @Override // okhttp3.Callback
                    public void onFailure(Call call2, IOException iOException) {
                        IMSLog.i(HttpController.TAG, "HTTP Request " + generateRandomString + " " + simpleName + " failed: " + IMSLog.numberChecker(call2.request().url().toString()) + " with " + call2.request().method() + " Reason: " + iOException.getMessage());
                        HttpController.listToDumpFormat(LogClass.MCS_HTTP_RESPONSE, httpRequestParams, generateRandomString, "IOException");
                        httpRequestParams.getCallback().onFail(iOException);
                    }
                });
            } catch (IllegalStateException e) {
                e.printStackTrace();
                httpRequestParams.getCallback().onFail(new IOException("okhttp malformed response"));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void listToDumpFormat(int i, HttpRequestParams httpRequestParams, String str, String str2) {
        try {
            ArrayList arrayList = new ArrayList();
            arrayList.add(0, Integer.toString(httpRequestParams.getPhoneId()));
            arrayList.add(1, str);
            arrayList.add(2, httpRequestParams.getClass().getSimpleName().replaceAll(CLASS_NAME_PREFIX, ""));
            arrayList.add(3, httpRequestParams.getMethod().name());
            if (!TextUtils.isEmpty(str2)) {
                arrayList.add(4, str2);
            }
            IMSLog.c(i, String.join(",", arrayList));
        } catch (Exception unused) {
            IMSLog.e(TAG, "listToDumpFormat has an exception");
        }
    }

    public HttpResponseParams syncExecute(HttpRequestParams httpRequestParams) {
        Call call;
        Response response;
        if (!isValidRequestParam(httpRequestParams, false) || (call = getCall(httpRequestParams)) == null) {
            return null;
        }
        String generateRandomString = generateRandomString(100000);
        IMSLog.s(TAG, "HTTP Request " + generateRandomString + " " + httpRequestParams);
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
            response = null;
        }
        if (response != null) {
            HttpResponseParams buildResponse = HttpResponseBuilder.buildResponse(response);
            IMSLog.s(TAG, "HTTP response: " + generateRandomString + " " + buildResponse);
            return buildResponse;
        }
        IMSLog.i(TAG, "HTTP response: " + generateRandomString + " null");
        return null;
    }

    private Call getCall(HttpRequestParams httpRequestParams) {
        Request buildRequest = HttpRequestBuilder.buildRequest(httpRequestParams);
        if (buildRequest == null) {
            IMSLog.e(TAG, "getCall(): okhttp request build failure");
            return null;
        }
        return getOkHttpClient(httpRequestParams).newCall(buildRequest);
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x00e5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected okhttp3.OkHttpClient getOkHttpClient(com.sec.internal.helper.httpclient.HttpRequestParams r8) {
        /*
            r7 = this;
            okhttp3.OkHttpClient r7 = com.sec.internal.helper.httpclient.HttpController.sOkHttpClient
            okhttp3.OkHttpClient$Builder r7 = r7.newBuilder()
            long r0 = r8.getConnectionTimeout()
            r2 = 0
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            r4 = 2147483647(0x7fffffff, double:1.060997895E-314)
            if (r0 < 0) goto L24
            long r0 = r8.getConnectionTimeout()
            int r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r0 > 0) goto L24
            long r0 = r8.getConnectionTimeout()
            java.util.concurrent.TimeUnit r6 = java.util.concurrent.TimeUnit.MILLISECONDS
            r7.connectTimeout(r0, r6)
        L24:
            long r0 = r8.getReadTimeout()
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 < 0) goto L3d
            long r0 = r8.getReadTimeout()
            int r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r0 > 0) goto L3d
            long r0 = r8.getReadTimeout()
            java.util.concurrent.TimeUnit r6 = java.util.concurrent.TimeUnit.MILLISECONDS
            r7.readTimeout(r0, r6)
        L3d:
            long r0 = r8.getWriteTimeout()
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 < 0) goto L56
            long r0 = r8.getWriteTimeout()
            int r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r0 > 0) goto L56
            long r0 = r8.getWriteTimeout()
            java.util.concurrent.TimeUnit r2 = java.util.concurrent.TimeUnit.MILLISECONDS
            r7.writeTimeout(r0, r2)
        L56:
            okhttp3.Dns r0 = r8.getDns()
            if (r0 == 0) goto L63
            okhttp3.Dns r0 = r8.getDns()
            r7.dns(r0)
        L63:
            javax.net.SocketFactory r0 = r8.getSocketFactory()
            if (r0 == 0) goto L70
            javax.net.SocketFactory r0 = r8.getSocketFactory()
            r7.socketFactory(r0)
        L70:
            boolean r0 = r8.getFollowRedirects()
            r7.followRedirects(r0)
            boolean r0 = r8.getRetryOnConnectionFailure()
            r7.retryOnConnectionFailure(r0)
            javax.net.ssl.TrustManager[] r0 = getTrustAllCertMangers()     // Catch: java.lang.Exception -> Lf1
            r1 = 0
            r1 = r0[r1]     // Catch: java.lang.Exception -> Lf1
            javax.net.ssl.X509TrustManager r1 = (javax.net.ssl.X509TrustManager) r1     // Catch: java.lang.Exception -> Lf1
            java.lang.String r2 = r8.getUrl()     // Catch: java.lang.Exception -> Lf1
            java.lang.String r3 = "https://wsg"
            boolean r2 = r2.startsWith(r3)     // Catch: java.lang.Exception -> Lf1
            if (r2 != 0) goto Ld8
            boolean r2 = com.sec.internal.helper.httpclient.HttpController.mIsDebugHttps     // Catch: java.lang.Exception -> Lf1
            if (r2 == 0) goto L98
            goto Ld8
        L98:
            boolean r2 = r8.getUseTls()     // Catch: java.lang.Exception -> Lf1
            if (r2 == 0) goto Ldf
            boolean r2 = r8.isReuseConnection()     // Catch: java.lang.Exception -> Lf1
            if (r2 == 0) goto Lbe
            javax.net.ssl.SSLSocketFactory r0 = createSslSocketFactory(r0)     // Catch: java.lang.Exception -> Lf1
            r7.sslSocketFactory(r0, r1)     // Catch: java.lang.Exception -> Lf1
            int r0 = r8.getCipherSuiteType()     // Catch: java.lang.Exception -> Lf1
            java.util.List r0 = getConnectionSpecs(r0)     // Catch: java.lang.Exception -> Lf1
            r7.connectionSpecs(r0)     // Catch: java.lang.Exception -> Lf1
            javax.net.ssl.HostnameVerifier r0 = getHostnameVerifier()     // Catch: java.lang.Exception -> Lf1
            r7.hostnameVerifier(r0)     // Catch: java.lang.Exception -> Lf1
            goto Ldf
        Lbe:
            javax.net.ssl.SSLSocketFactory r0 = createSslSocketFactory(r0)     // Catch: java.lang.Exception -> Lf1
            r7.sslSocketFactory(r0, r1)     // Catch: java.lang.Exception -> Lf1
            okhttp3.ConnectionSpec r0 = configConnectionSpec()     // Catch: java.lang.Exception -> Lf1
            java.util.List r0 = java.util.Collections.singletonList(r0)     // Catch: java.lang.Exception -> Lf1
            r7.connectionSpecs(r0)     // Catch: java.lang.Exception -> Lf1
            javax.net.ssl.HostnameVerifier r0 = createHostnameVerifier()     // Catch: java.lang.Exception -> Lf1
            r7.hostnameVerifier(r0)     // Catch: java.lang.Exception -> Lf1
            goto Ldf
        Ld8:
            javax.net.ssl.SSLSocketFactory r0 = createSslSocketFactory(r0)     // Catch: java.lang.Exception -> Lf1
            r7.sslSocketFactory(r0, r1)     // Catch: java.lang.Exception -> Lf1
        Ldf:
            boolean r0 = r8.getUseProxy()
            if (r0 == 0) goto Lec
            java.net.Proxy r8 = r8.getProxy()
            r7.proxy(r8)
        Lec:
            okhttp3.OkHttpClient r7 = r7.build()
            return r7
        Lf1:
            java.lang.String r8 = com.sec.internal.helper.httpclient.HttpController.TAG
            java.lang.String r0 = "Could not load keystore "
            com.sec.internal.log.IMSLog.d(r8, r0)
            okhttp3.OkHttpClient r7 = r7.build()
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sec.internal.helper.httpclient.HttpController.getOkHttpClient(com.sec.internal.helper.httpclient.HttpRequestParams):okhttp3.OkHttpClient");
    }

    protected static List<ConnectionSpec> getConnectionSpecs() {
        return getConnectionSpecs(0);
    }

    protected static List<ConnectionSpec> getConnectionSpecs(int i) {
        List<ConnectionSpec> immutableListOf;
        IMSLog.i(TAG, "getConnectionSpecs:: type =" + i);
        List<ConnectionSpec> list = mConnectionSpecs;
        if (list == null) {
            synchronized (HttpController.class) {
                list = mConnectionSpecs;
                if (list == null) {
                    if (i == 1) {
                        immutableListOf = Util.immutableListOf(configConnectionSpecForSpecificOperator(), ConnectionSpec.CLEARTEXT);
                        mConnectionSpecs = immutableListOf;
                    } else {
                        immutableListOf = Util.immutableListOf(configConnectionSpec(), ConnectionSpec.CLEARTEXT);
                        mConnectionSpecs = immutableListOf;
                    }
                    list = immutableListOf;
                }
            }
        }
        return list;
    }

    protected static HostnameVerifier getHostnameVerifier() {
        HostnameVerifier hostnameVerifier = mHostnameVerifier;
        if (hostnameVerifier == null) {
            synchronized (HttpController.class) {
                hostnameVerifier = mHostnameVerifier;
                if (hostnameVerifier == null) {
                    hostnameVerifier = createHostnameVerifier();
                    mHostnameVerifier = hostnameVerifier;
                }
            }
        }
        return hostnameVerifier;
    }

    protected static HostnameVerifier createHostnameVerifier() {
        return new HostnameVerifier() { // from class: com.sec.internal.helper.httpclient.HttpController$$ExternalSyntheticLambda0
            @Override // javax.net.ssl.HostnameVerifier
            public final boolean verify(String str, SSLSession sSLSession) {
                boolean lambda$createHostnameVerifier$0;
                lambda$createHostnameVerifier$0 = HttpController.lambda$createHostnameVerifier$0(str, sSLSession);
                return lambda$createHostnameVerifier$0;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$createHostnameVerifier$0(String str, SSLSession sSLSession) {
        OkHostnameVerifier okHostnameVerifier = OkHostnameVerifier.INSTANCE;
        boolean z = false;
        try {
            boolean z2 = false;
            for (Certificate certificate : sSLSession.getPeerCertificates()) {
                try {
                    z2 = okHostnameVerifier.verify(str, (X509Certificate) certificate);
                    if (z2) {
                        return z2;
                    }
                } catch (SSLException unused) {
                    z = z2;
                    IMSLog.e(TAG, "SSL Exception with HostNameVerify Fail");
                    return z;
                }
            }
            return z2;
        } catch (SSLException unused2) {
        }
    }

    private boolean isValidRequestParam(HttpRequestParams httpRequestParams, boolean z) {
        if (httpRequestParams == null || httpRequestParams.getMethod() == null) {
            IMSLog.e(TAG, "isValidRequestParam(): invalid param, vail");
            return false;
        }
        if (z && httpRequestParams.getCallback() == null) {
            IMSLog.e(TAG, "isValidRequestParam(): callback is null for async call");
            return false;
        }
        if (URLUtil.isValidUrl(httpRequestParams.getUrl())) {
            return true;
        }
        IMSLog.e(TAG, "isValidRequestParam(): invalid uri: " + IMSLog.numberChecker(httpRequestParams.getUrl()));
        return false;
    }

    private String generateRandomString(int i) {
        return String.valueOf(random.nextInt(i));
    }
}
