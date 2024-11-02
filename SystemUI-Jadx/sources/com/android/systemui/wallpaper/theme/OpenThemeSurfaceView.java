package com.android.systemui.wallpaper.theme;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public abstract class OpenThemeSurfaceView extends SurfaceView implements SurfaceHolder.Callback, LockscreenCallback {
    public final String TAG;
    public FrameDrawThread mDrawThread;
    public final SurfaceHolder mHolder;
    public boolean mIsCreated;
    public boolean mIsScreenOn;
    public int mMinInterval;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class FrameDrawThread extends Thread {
        public boolean isSuspended;
        public final SurfaceHolder mHolder;
        public int mCount = 0;
        public int mTick = 0;
        public int mMinInterval = 34;
        public boolean isRunning = true;

        public FrameDrawThread(SurfaceHolder surfaceHolder) {
            this.mHolder = surfaceHolder;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            Canvas canvas;
            SurfaceHolder surfaceHolder;
            long nanoTime = System.nanoTime();
            while (this.isRunning) {
                long currentTimeMillis = System.currentTimeMillis();
                synchronized (this) {
                    while (true) {
                        canvas = null;
                        try {
                            if (!this.isSuspended || this.mTick <= 0) {
                                break;
                            }
                            this.mTick = 0;
                            wait();
                        } catch (Exception unused) {
                            if (0 != 0) {
                                surfaceHolder = this.mHolder;
                            }
                        } catch (Throwable th) {
                            if (0 != 0) {
                                this.mHolder.unlockCanvasAndPost(null);
                            }
                            throw th;
                        }
                    }
                    canvas = this.mHolder.lockCanvas();
                    if (canvas != null) {
                        OpenThemeSurfaceView.this.drawFrame(canvas);
                    }
                    if (canvas != null) {
                        surfaceHolder = this.mHolder;
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
                this.mTick++;
                int i = this.mCount + 1;
                this.mCount = i;
                if (i == 60) {
                    long nanoTime2 = System.nanoTime();
                    String str = OpenThemeSurfaceView.this.TAG;
                    Log.d(str, "fps: " + (Math.round((1.0E11d / (nanoTime2 - nanoTime)) * 60.0d) / 100.0d));
                    this.mCount = 0;
                    nanoTime = nanoTime2;
                }
                long currentTimeMillis2 = System.currentTimeMillis();
                long j = currentTimeMillis2 - currentTimeMillis;
                try {
                    int i2 = this.mMinInterval;
                    if (j < i2) {
                        if ((i2 + currentTimeMillis) - currentTimeMillis2 > 150) {
                            Thread.sleep(100L);
                        } else {
                            Thread.sleep((i2 + currentTimeMillis) - currentTimeMillis2);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public OpenThemeSurfaceView(Context context) {
        super(context);
        this.mMinInterval = 0;
        this.TAG = "OpenThemeSurfaceView";
        SurfaceHolder holder = getHolder();
        this.mHolder = holder;
        holder.addCallback(this);
    }

    public abstract void drawFrame(Canvas canvas);

    @Override // android.view.SurfaceView, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        FrameDrawThread frameDrawThread = this.mDrawThread;
        if (frameDrawThread != null) {
            frameDrawThread.isRunning = false;
            frameDrawThread.interrupt();
            this.mDrawThread = null;
            Log.d(this.TAG, "stopThread");
        }
    }

    @Override // com.android.systemui.wallpaper.theme.LockscreenCallback
    public final void screenTurnedOff() {
        this.mIsScreenOn = false;
        if (this.mDrawThread != null) {
            Log.d(this.TAG, "suspendThread");
            FrameDrawThread frameDrawThread = this.mDrawThread;
            frameDrawThread.mTick = 0;
            frameDrawThread.isSuspended = true;
        }
    }

    @Override // com.android.systemui.wallpaper.theme.LockscreenCallback
    public final void screenTurnedOn() {
        this.mIsScreenOn = true;
        if (this.mDrawThread != null && this.mIsCreated) {
            Log.d(this.TAG, "resumeThread");
            FrameDrawThread frameDrawThread = this.mDrawThread;
            synchronized (frameDrawThread) {
                frameDrawThread.isSuspended = false;
                frameDrawThread.notify();
            }
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public final void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.d(this.TAG, "surfaceCreated");
        this.mIsCreated = true;
        FrameDrawThread frameDrawThread = this.mDrawThread;
        if (frameDrawThread == null) {
            FrameDrawThread frameDrawThread2 = new FrameDrawThread(this.mHolder);
            this.mDrawThread = frameDrawThread2;
            if (!this.mIsScreenOn) {
                frameDrawThread2.isSuspended = true;
            }
            int i = this.mMinInterval;
            if (i > 0) {
                frameDrawThread2.mMinInterval = i;
            }
            frameDrawThread2.start();
            Log.d(this.TAG, "startThread");
            return;
        }
        synchronized (frameDrawThread) {
            frameDrawThread.isSuspended = false;
            frameDrawThread.notify();
        }
        if (!this.mIsScreenOn) {
            FrameDrawThread frameDrawThread3 = this.mDrawThread;
            frameDrawThread3.mTick = 0;
            frameDrawThread3.isSuspended = true;
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public final void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        Log.d(this.TAG, "surfaceDestroyed");
        this.mIsCreated = false;
        if (this.mDrawThread != null) {
            Log.d(this.TAG, "suspendThread");
            FrameDrawThread frameDrawThread = this.mDrawThread;
            frameDrawThread.mTick = 0;
            frameDrawThread.isSuspended = true;
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public final void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
    }
}
