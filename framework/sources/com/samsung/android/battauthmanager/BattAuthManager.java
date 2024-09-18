package com.samsung.android.battauthmanager;

import android.app.AppGlobals;
import android.content.Context;
import android.content.pm.IPackageManager;
import android.os.HandlerThread;
import android.util.Log;

/* loaded from: classes5.dex */
public class BattAuthManager {
    private static final String TAG = "BattAuthManager";
    private Context mContext;
    private HandlerThread mHandlerThread;
    WpcAuthenticator mWpcAuthenticator;

    public BattAuthManager(Context context) {
        Log.i(TAG, "BattAuthManager start");
        this.mContext = context;
        initWpcAuthenticator();
    }

    private void initWpcAuthenticator() {
        try {
            IPackageManager pm = AppGlobals.getPackageManager();
            boolean isFirstBoot = pm.isFirstBoot();
            Log.i(TAG, "isFirstBoot : " + isFirstBoot);
            if (this.mWpcAuthenticator == null) {
                HandlerThread handlerThread = new HandlerThread(TAG);
                this.mHandlerThread = handlerThread;
                handlerThread.start();
                WpcAuthenticator wpcAuthenticator = new WpcAuthenticator(this.mContext, this.mHandlerThread.getLooper());
                this.mWpcAuthenticator = wpcAuthenticator;
                if (isFirstBoot) {
                    wpcAuthenticator.removeDigests();
                }
            }
        } catch (Throwable e) {
            Log.i(TAG, "BattAuthManager error");
            e.printStackTrace();
        }
    }
}
