package com.samsung.server.wallpaper;

import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.TextUtils;
import com.android.server.wallpaper.WallpaperManagerService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/* loaded from: classes2.dex */
public class OMCWallpaper {
    public final WallpaperManagerService.SemCallback mCallback;
    public final Context mContext;
    public final Handler mHandler = new Handler(Looper.getMainLooper()) { // from class: com.samsung.server.wallpaper.OMCWallpaper.1
        public AnonymousClass1(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            OMCWallpaper.this.mCallback.updateOmcWallpaper();
        }
    };
    public OMCWallpaperUpdatedReceiver mOMCWallpaperUpdatedReceiver;
    public final SemWallpaperManagerService mService;

    public OMCWallpaper(Context context, WallpaperManagerService.SemCallback semCallback, SemWallpaperManagerService semWallpaperManagerService) {
        Log.d("OMCWallpaper", "OMCWallpaper");
        this.mContext = context;
        this.mCallback = semCallback;
        this.mService = semWallpaperManagerService;
    }

    /* renamed from: com.samsung.server.wallpaper.OMCWallpaper$1 */
    /* loaded from: classes2.dex */
    public class AnonymousClass1 extends Handler {
        public AnonymousClass1(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            OMCWallpaper.this.mCallback.updateOmcWallpaper();
        }
    }

    public void updateOmcWallpaper(String str) {
        Log.d("OMCWallpaper", "OMCWallpaperUpdatedReceiver : onReceive:" + str);
        if ("com.samsung.intent.action.RSCUPDATE_START".equalsIgnoreCase(str)) {
            if (this.mHandler.hasMessages(1)) {
                this.mHandler.removeMessages(1);
            }
            this.mHandler.sendEmptyMessage(1);
        }
    }

    public void registerOMCWallpaperUpdatedReceiver() {
        Log.d("OMCWallpaper", "registerOMCWallpaperUpdatedReceiver");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.intent.action.RSCUPDATE_START");
        OMCWallpaperUpdatedReceiver oMCWallpaperUpdatedReceiver = new OMCWallpaperUpdatedReceiver();
        this.mOMCWallpaperUpdatedReceiver = oMCWallpaperUpdatedReceiver;
        this.mContext.registerReceiver(oMCWallpaperUpdatedReceiver, intentFilter);
    }

    /* loaded from: classes2.dex */
    public class OMCWallpaperUpdatedReceiver extends BroadcastReceiver {
        public /* synthetic */ OMCWallpaperUpdatedReceiver(OMCWallpaper oMCWallpaper, OMCWallpaperUpdatedReceiverIA oMCWallpaperUpdatedReceiverIA) {
            this();
        }

        public OMCWallpaperUpdatedReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            OMCWallpaper.this.updateOmcWallpaper(intent.getAction());
        }
    }

    public Bitmap getOperatorWallpaperBitmap(int i) {
        FileInputStream fileInputStream;
        Log.d("OMCWallpaper", "getOperatorWallpaperBitmap()");
        File defaultWallpaperFile = WallpaperManager.getDefaultWallpaperFile(this.mContext, i);
        Bitmap bitmap = null;
        if (defaultWallpaperFile == null) {
            return null;
        }
        try {
            fileInputStream = new FileInputStream(defaultWallpaperFile);
        } catch (IOException e) {
            Log.w("OMCWallpaper", "getDefaultWallpaperFile error:", e);
            fileInputStream = null;
        }
        try {
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        if (fileInputStream == null) {
            return null;
        }
        try {
            try {
                bitmap = BitmapFactory.decodeStream(fileInputStream);
                fileInputStream.close();
            } catch (Throwable th) {
                try {
                    fileInputStream.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
                throw th;
            }
        } catch (OutOfMemoryError e4) {
            Log.w("OMCWallpaper", "Can't decode stream", e4);
            fileInputStream.close();
        }
        return bitmap;
    }

    public boolean needToUpdateOMCWallpaper(File file) {
        if (file != null && file.exists() && file.length() > 0) {
            Log.e("OMCWallpaper", "user wallpaper is being used");
            return false;
        }
        if (WallpaperManager.getOMCWallpaperFile(this.mContext, 1, this.mService.mCMFWallpaper.getDeviceColor()) != null) {
            return true;
        }
        Log.e("OMCWallpaper", "no omc wallpaper");
        return false;
    }

    public void checkTSSActivation(boolean z) {
        String str;
        Log.d("OMCWallpaper", "checkTSSActivation");
        File file = new File(Environment.getUserSystemDirectory(0) + "/wallpaper_status");
        if (file.exists()) {
            try {
                str = SemWallpaperManagerService.getStringFromFile(file.getPath());
            } catch (Exception unused) {
                str = null;
            }
        } else {
            str = "false";
        }
        String str2 = SystemProperties.get("mdc.singlesku.activated");
        Log.d("OMCWallpaper", "checkTSSActivation, old= " + str + ", new=" + str2);
        if (!str2.equals(str)) {
            this.mCallback.handleOMCWallpaperUpdated();
            saveTSSActivation(str2);
            saveTSSActivationSettings(str2);
        } else if ("true".equals(str)) {
            int i = Settings.System.getInt(this.mContext.getContentResolver(), "tss_activated", -1);
            if (z && i == -1) {
                saveTSSActivationSettings(str);
            }
        }
    }

    public final void saveTSSActivation(String str) {
        Log.d("OMCWallpaper", "saveTSSActivation, " + str);
        File file = new File(Environment.getUserSystemDirectory(0) + "/wallpaper_status");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream fileOutputStream = null;
        try {
            try {
                try {
                    FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                    try {
                        fileOutputStream2.write(str.getBytes(StandardCharsets.UTF_8));
                        Log.d("OMCWallpaper", "save done");
                        fileOutputStream2.close();
                    } catch (Exception e2) {
                        e = e2;
                        fileOutputStream = fileOutputStream2;
                        e.printStackTrace();
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                    } catch (Throwable th) {
                        th = th;
                        fileOutputStream = fileOutputStream2;
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception e4) {
                e = e4;
            }
        } catch (IOException e5) {
            e5.printStackTrace();
        }
    }

    public final void saveTSSActivationSettings(String str) {
        if (WallpaperManager.isDefaultOperatorWallpaper(this.mContext, 2, this.mService.mCMFWallpaper.getDeviceColor()) && !TextUtils.isEmpty(str) && str.equals("true")) {
            Settings.System.putInt(this.mContext.getContentResolver(), "tss_activated", 1);
        }
    }
}
