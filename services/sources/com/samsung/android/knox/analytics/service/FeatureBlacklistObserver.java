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

/* loaded from: classes2.dex */
public class FeatureBlacklistObserver {
    public static final String HT_NAME = "FeatureBlacklistObserver";
    public static final String TAG = "[KnoxAnalytics] " + FeatureBlacklistContentObserver.class.getSimpleName();
    public final Context mContext;
    public FeatureBlacklistContentObserver mFeatureBlacklistContentObserver;
    public List mFeaturesBlacklistCache;
    public HandlerThread mHandlerThread;

    /* loaded from: classes2.dex */
    public class FeatureBlacklistContentObserver extends ContentObserver {
        public FeatureBlacklistContentObserver(Handler handler) {
            super(handler);
            Log.d(FeatureBlacklistObserver.TAG, "FeatureBlacklistContentObserver()");
            updateFeatureBlacklistCache();
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            Log.d(FeatureBlacklistObserver.TAG, "FeatureBlacklist.onChange(" + uri.toString() + ")");
            updateFeatureBlacklistCache();
        }

        public final void updateFeatureBlacklistCache() {
            FeatureBlacklistObserver featureBlacklistObserver = FeatureBlacklistObserver.this;
            featureBlacklistObserver.mFeaturesBlacklistCache = KnoxAnalyticsQueryResolver.getFeaturesBlacklist(featureBlacklistObserver.mContext);
            if (FeatureBlacklistObserver.this.mFeaturesBlacklistCache == null) {
                Log.e(FeatureBlacklistObserver.TAG, "FeatureBlacklist.updateFeatureBlacklistCache(): blacklist is null!");
            }
        }
    }

    public FeatureBlacklistObserver(Context context) {
        this.mContext = context;
    }

    public List getFeatureBlacklist() {
        return this.mFeaturesBlacklistCache;
    }

    public void start() {
        Log.d(TAG, "start()");
        HandlerThread handlerThread = new HandlerThread(HT_NAME);
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mFeatureBlacklistContentObserver = new FeatureBlacklistContentObserver(this.mHandlerThread.getThreadHandler());
        this.mContext.getContentResolver().registerContentObserver(Contract.FeaturesBlacklist.CONTENT_URI, true, this.mFeatureBlacklistContentObserver, 0);
    }

    public void stop() {
        Log.d(TAG, "stop()");
        if (this.mFeatureBlacklistContentObserver != null) {
            this.mContext.getContentResolver().unregisterContentObserver(this.mFeatureBlacklistContentObserver);
        }
        this.mFeatureBlacklistContentObserver = null;
        HandlerThread handlerThread = this.mHandlerThread;
        if (handlerThread != null) {
            handlerThread.quitSafely();
        }
        this.mHandlerThread = null;
    }
}
