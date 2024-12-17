package com.samsung.android.knox.analytics.service;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import com.samsung.android.knox.analytics.database.Contract;
import com.samsung.android.knox.analytics.util.KnoxAnalyticsQueryResolver;
import com.samsung.android.knox.analytics.util.Log;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class FeatureWhitelistObserver {
    public static final String HT_NAME = "FeatureWhitelistObserver";
    public static final String TAG = "[KnoxAnalytics] FeatureWhitelistObserver";
    public final Context mContext;
    public FeatureWhitelistContentObserver mFeatureWhitelistContentObserver;
    public List mFeaturesWhitelistCache;
    public HandlerThread mHandlerThread;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FeatureWhitelistContentObserver extends ContentObserver {
        public FeatureWhitelistContentObserver(Handler handler) {
            super(handler);
            Log.d(FeatureWhitelistObserver.TAG, "FeatureWhitelistContentObserver()");
            updateFeatureWhitelistCache();
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            Log.d(FeatureWhitelistObserver.TAG, "FeatureWhitelist.onChange(" + uri.toString() + ")");
            updateFeatureWhitelistCache();
        }

        public final void updateFeatureWhitelistCache() {
            FeatureWhitelistObserver featureWhitelistObserver = FeatureWhitelistObserver.this;
            featureWhitelistObserver.mFeaturesWhitelistCache = KnoxAnalyticsQueryResolver.getFeaturesWhitelist(featureWhitelistObserver.mContext);
            if (FeatureWhitelistObserver.this.mFeaturesWhitelistCache == null) {
                Log.e(FeatureWhitelistObserver.TAG, "FeatureWhitelist.updateFeatureWhitelistCache(): Whitelist is null!");
            }
        }
    }

    public FeatureWhitelistObserver(Context context) {
        this.mContext = context;
    }

    public final List getFeatureWhitelist() {
        return this.mFeaturesWhitelistCache;
    }

    public final void start() {
        Log.d(TAG, "start()");
        HandlerThread handlerThread = new HandlerThread(HT_NAME);
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mFeatureWhitelistContentObserver = new FeatureWhitelistContentObserver(this.mHandlerThread.getThreadHandler());
        this.mContext.getContentResolver().registerContentObserver(Contract.FeaturesWhitelist.CONTENT_URI, true, this.mFeatureWhitelistContentObserver, 0);
    }

    public final void stop() {
        Log.d(TAG, "stop()");
        if (this.mFeatureWhitelistContentObserver != null) {
            this.mContext.getContentResolver().unregisterContentObserver(this.mFeatureWhitelistContentObserver);
        }
        this.mFeatureWhitelistContentObserver = null;
        HandlerThread handlerThread = this.mHandlerThread;
        if (handlerThread != null) {
            handlerThread.quitSafely();
        }
        this.mHandlerThread = null;
    }
}
