package okhttp3.internal.cache;

import com.sec.internal.helper.httpclient.HttpController;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;
import okhttp3.internal.http.DatesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CacheStrategy.kt */
/* loaded from: classes.dex */
public final class CacheStrategy {

    @NotNull
    public static final Companion Companion = new Companion(null);

    @Nullable
    private final Response cacheResponse;

    @Nullable
    private final Request networkRequest;

    public CacheStrategy(@Nullable Request request, @Nullable Response response) {
        this.networkRequest = request;
        this.cacheResponse = response;
    }

    @Nullable
    public final Request getNetworkRequest() {
        return this.networkRequest;
    }

    @Nullable
    public final Response getCacheResponse() {
        return this.cacheResponse;
    }

    /* compiled from: CacheStrategy.kt */
    public static final class Factory {
        private int ageSeconds;

        @Nullable
        private final Response cacheResponse;

        @Nullable
        private String etag;

        @Nullable
        private Date expires;

        @Nullable
        private Date lastModified;

        @Nullable
        private String lastModifiedString;
        private final long nowMillis;
        private long receivedResponseMillis;

        @NotNull
        private final Request request;
        private long sentRequestMillis;

        @Nullable
        private Date servedDate;

        @Nullable
        private String servedDateString;

        public Factory(long j, @NotNull Request request, @Nullable Response response) {
            boolean equals;
            boolean equals2;
            boolean equals3;
            boolean equals4;
            boolean equals5;
            Intrinsics.checkNotNullParameter(request, "request");
            this.nowMillis = j;
            this.request = request;
            this.cacheResponse = response;
            this.ageSeconds = -1;
            if (response != null) {
                this.sentRequestMillis = response.sentRequestAtMillis();
                this.receivedResponseMillis = response.receivedResponseAtMillis();
                Headers headers = response.headers();
                int size = headers.size();
                int i = 0;
                while (i < size) {
                    int i2 = i + 1;
                    String name = headers.name(i);
                    String value = headers.value(i);
                    equals = StringsKt__StringsJVMKt.equals(name, "Date", true);
                    if (equals) {
                        this.servedDate = DatesKt.toHttpDateOrNull(value);
                        this.servedDateString = value;
                    } else {
                        equals2 = StringsKt__StringsJVMKt.equals(name, HttpController.HEADER_EXPIRES, true);
                        if (equals2) {
                            this.expires = DatesKt.toHttpDateOrNull(value);
                        } else {
                            equals3 = StringsKt__StringsJVMKt.equals(name, HttpController.HEADER_LAST_MODIFIED, true);
                            if (equals3) {
                                this.lastModified = DatesKt.toHttpDateOrNull(value);
                                this.lastModifiedString = value;
                            } else {
                                equals4 = StringsKt__StringsJVMKt.equals(name, HttpController.HEADER_ETAG, true);
                                if (equals4) {
                                    this.etag = value;
                                } else {
                                    equals5 = StringsKt__StringsJVMKt.equals(name, "Age", true);
                                    if (equals5) {
                                        this.ageSeconds = Util.toNonNegativeInt(value, -1);
                                    }
                                }
                            }
                        }
                    }
                    i = i2;
                }
            }
        }

        private final boolean isFreshnessLifetimeHeuristic() {
            Response response = this.cacheResponse;
            Intrinsics.checkNotNull(response);
            return response.cacheControl().maxAgeSeconds() == -1 && this.expires == null;
        }

        @NotNull
        public final CacheStrategy compute() {
            CacheStrategy computeCandidate = computeCandidate();
            return (computeCandidate.getNetworkRequest() == null || !this.request.cacheControl().onlyIfCached()) ? computeCandidate : new CacheStrategy(null, null);
        }

        private final CacheStrategy computeCandidate() {
            String str;
            if (this.cacheResponse == null) {
                return new CacheStrategy(this.request, null);
            }
            if (this.request.isHttps() && this.cacheResponse.handshake() == null) {
                return new CacheStrategy(this.request, null);
            }
            if (!CacheStrategy.Companion.isCacheable(this.cacheResponse, this.request)) {
                return new CacheStrategy(this.request, null);
            }
            CacheControl cacheControl = this.request.cacheControl();
            if (cacheControl.noCache() || hasConditions(this.request)) {
                return new CacheStrategy(this.request, null);
            }
            CacheControl cacheControl2 = this.cacheResponse.cacheControl();
            long cacheResponseAge = cacheResponseAge();
            long computeFreshnessLifetime = computeFreshnessLifetime();
            if (cacheControl.maxAgeSeconds() != -1) {
                computeFreshnessLifetime = Math.min(computeFreshnessLifetime, TimeUnit.SECONDS.toMillis(cacheControl.maxAgeSeconds()));
            }
            long j = 0;
            long millis = cacheControl.minFreshSeconds() != -1 ? TimeUnit.SECONDS.toMillis(cacheControl.minFreshSeconds()) : 0L;
            if (!cacheControl2.mustRevalidate() && cacheControl.maxStaleSeconds() != -1) {
                j = TimeUnit.SECONDS.toMillis(cacheControl.maxStaleSeconds());
            }
            if (!cacheControl2.noCache()) {
                long j2 = millis + cacheResponseAge;
                if (j2 < j + computeFreshnessLifetime) {
                    Response.Builder newBuilder = this.cacheResponse.newBuilder();
                    if (j2 >= computeFreshnessLifetime) {
                        newBuilder.addHeader("Warning", "110 HttpURLConnection \"Response is stale\"");
                    }
                    if (cacheResponseAge > 86400000 && isFreshnessLifetimeHeuristic()) {
                        newBuilder.addHeader("Warning", "113 HttpURLConnection \"Heuristic expiration\"");
                    }
                    return new CacheStrategy(null, newBuilder.build());
                }
            }
            String str2 = this.etag;
            if (str2 != null) {
                str = HttpController.HEADER_IF_NONE_MATCH;
            } else {
                if (this.lastModified != null) {
                    str2 = this.lastModifiedString;
                } else if (this.servedDate != null) {
                    str2 = this.servedDateString;
                } else {
                    return new CacheStrategy(this.request, null);
                }
                str = "If-Modified-Since";
            }
            Headers.Builder newBuilder2 = this.request.headers().newBuilder();
            Intrinsics.checkNotNull(str2);
            newBuilder2.addLenient$okhttp(str, str2);
            return new CacheStrategy(this.request.newBuilder().headers(newBuilder2.build()).build(), this.cacheResponse);
        }

        private final long computeFreshnessLifetime() {
            Long valueOf;
            Response response = this.cacheResponse;
            Intrinsics.checkNotNull(response);
            if (response.cacheControl().maxAgeSeconds() != -1) {
                return TimeUnit.SECONDS.toMillis(r0.maxAgeSeconds());
            }
            Date date = this.expires;
            if (date != null) {
                Date date2 = this.servedDate;
                valueOf = date2 != null ? Long.valueOf(date2.getTime()) : null;
                long time = date.getTime() - (valueOf == null ? this.receivedResponseMillis : valueOf.longValue());
                if (time > 0) {
                    return time;
                }
                return 0L;
            }
            if (this.lastModified == null || this.cacheResponse.request().url().query() != null) {
                return 0L;
            }
            Date date3 = this.servedDate;
            valueOf = date3 != null ? Long.valueOf(date3.getTime()) : null;
            long longValue = valueOf == null ? this.sentRequestMillis : valueOf.longValue();
            Date date4 = this.lastModified;
            Intrinsics.checkNotNull(date4);
            long time2 = longValue - date4.getTime();
            if (time2 > 0) {
                return time2 / 10;
            }
            return 0L;
        }

        private final long cacheResponseAge() {
            Date date = this.servedDate;
            long max = date != null ? Math.max(0L, this.receivedResponseMillis - date.getTime()) : 0L;
            int i = this.ageSeconds;
            if (i != -1) {
                max = Math.max(max, TimeUnit.SECONDS.toMillis(i));
            }
            long j = this.receivedResponseMillis;
            return max + (j - this.sentRequestMillis) + (this.nowMillis - j);
        }

        private final boolean hasConditions(Request request) {
            return (request.header("If-Modified-Since") == null && request.header(HttpController.HEADER_IF_NONE_MATCH) == null) ? false : true;
        }
    }

    /* compiled from: CacheStrategy.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean isCacheable(@NotNull Response response, @NotNull Request request) {
            Intrinsics.checkNotNullParameter(response, "response");
            Intrinsics.checkNotNullParameter(request, "request");
            int code = response.code();
            if (code != 200 && code != 410 && code != 414 && code != 501 && code != 203 && code != 204) {
                if (code != 307) {
                    if (code != 308 && code != 404 && code != 405) {
                        switch (code) {
                            case 300:
                            case 301:
                                break;
                            case 302:
                                break;
                            default:
                                return false;
                        }
                    }
                }
                if (Response.header$default(response, HttpController.HEADER_EXPIRES, null, 2, null) == null && response.cacheControl().maxAgeSeconds() == -1 && !response.cacheControl().isPublic() && !response.cacheControl().isPrivate()) {
                    return false;
                }
            }
            return (response.cacheControl().noStore() || request.cacheControl().noStore()) ? false : true;
        }
    }
}
