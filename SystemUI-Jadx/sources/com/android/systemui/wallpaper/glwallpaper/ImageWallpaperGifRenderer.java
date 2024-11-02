package com.android.systemui.wallpaper.glwallpaper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;
import com.android.keyguard.punchhole.VIDirector$$ExternalSyntheticOutline0;
import com.android.systemui.coverlauncher.utils.badge.NotificationListener$$ExternalSyntheticOutline0;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class ImageWallpaperGifRenderer {
    public Rect mBoundRect;
    public final Context mContext;
    public Movie mMovie;
    public final AnonymousClass1 mOnDrawHandler;
    public long mPausedMovieTime;
    public int mPlayedMovieIndex;
    public long mStartedMovieTime;
    public SurfaceHolder mSurfaceHolder;
    public boolean mVisible;
    public final Paint mMoviePaint = new Paint();
    public boolean mIsFinishedPlayGif = false;
    public boolean mIsPaused = false;
    public float mCenterCropScale = 1.0f;
    public final PointF mCenterCropOffset = new PointF(0.0f, 0.0f);

    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.wallpaper.glwallpaper.ImageWallpaperGifRenderer$1] */
    public ImageWallpaperGifRenderer(Context context, HandlerThread handlerThread) {
        this.mContext = context;
        this.mOnDrawHandler = new Handler(handlerThread.getThreadHandler().getLooper()) { // from class: com.android.systemui.wallpaper.glwallpaper.ImageWallpaperGifRenderer.1
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                if (message.what == 1001) {
                    ImageWallpaperGifRenderer imageWallpaperGifRenderer = ImageWallpaperGifRenderer.this;
                    if (imageWallpaperGifRenderer.mIsPaused) {
                        imageWallpaperGifRenderer.mPausedMovieTime = imageWallpaperGifRenderer.mPlayedMovieIndex;
                    } else {
                        imageWallpaperGifRenderer.drawGif(imageWallpaperGifRenderer.mSurfaceHolder, true);
                    }
                }
            }
        };
    }

    public final void adjustCenterCropScale(Rect rect, Rect rect2) {
        PointF pointF = this.mCenterCropOffset;
        if (rect != null) {
            float width = rect.width() / rect2.width();
            float height = rect.height() / rect2.height();
            this.mCenterCropScale = Math.max(width, height);
            pointF.x = (rect.width() - (rect2.width() * this.mCenterCropScale)) / 2.0f;
            pointF.y = (rect.height() - (rect2.height() * this.mCenterCropScale)) / 2.0f;
            StringBuilder m = VIDirector$$ExternalSyntheticOutline0.m("adjustScale  : ", width, " , ", height, " , ");
            m.append(this.mCenterCropScale);
            m.append(" , ");
            m.append(pointF.x);
            m.append(" , ");
            m.append(pointF.y);
            Log.i("ImageWallpaperGifRenderer", m.toString());
            return;
        }
        Log.e("ImageWallpaperGifRenderer", "adjustScale : bound or movie is NULL");
        this.mCenterCropScale = 1.0f;
        pointF.set(0.0f, 0.0f);
    }

    public final void drawGif(SurfaceHolder surfaceHolder, boolean z) {
        Canvas canvas;
        if (surfaceHolder == null) {
            Log.e("ImageWallpaperGifRenderer", "drawGif: holder is null!");
            return;
        }
        Movie movie = this.mMovie;
        if (movie == null) {
            Log.e("ImageWallpaperGifRenderer", "drawGif: mMovie is null!");
            return;
        }
        if (movie.width() > 0 && this.mMovie.height() > 0) {
            if (surfaceHolder.getSurface() != null && surfaceHolder.getSurface().isValid()) {
                canvas = surfaceHolder.lockCanvas();
            } else {
                canvas = null;
            }
            if (canvas == null) {
                Log.e("ImageWallpaperGifRenderer", "drawGif: canvas is null!");
                return;
            }
            if (this.mPlayedMovieIndex >= this.mMovie.duration()) {
                this.mStartedMovieTime = 0L;
            }
            try {
                long uptimeMillis = SystemClock.uptimeMillis();
                if (this.mStartedMovieTime == 0) {
                    this.mStartedMovieTime = (int) uptimeMillis;
                }
                int i = (int) (uptimeMillis - this.mStartedMovieTime);
                if (i > this.mMovie.duration()) {
                    i = this.mMovie.duration();
                }
                if (this.mIsFinishedPlayGif) {
                    i = 0;
                }
                this.mMovie.setTime(i);
                this.mPlayedMovieIndex = i;
                canvas.drawColor(EmergencyPhoneWidget.BG_COLOR);
                float f = this.mCenterCropScale;
                canvas.scale(f, f, 0.0f, 0.0f);
                Movie movie2 = this.mMovie;
                PointF pointF = this.mCenterCropOffset;
                movie2.draw(canvas, pointF.x, pointF.y, this.mMoviePaint);
            } catch (Exception e) {
                Log.i("ImageWallpaperGifRenderer", "drawGif: GIF Exception " + e.getMessage());
            }
            AnonymousClass1 anonymousClass1 = this.mOnDrawHandler;
            anonymousClass1.removeMessages(1001);
            if (z && this.mVisible && !this.mIsFinishedPlayGif) {
                anonymousClass1.sendEmptyMessageDelayed(1001, 50L);
            }
            try {
                surfaceHolder.unlockCanvasAndPost(canvas);
                return;
            } catch (IllegalArgumentException | IllegalStateException unused) {
                Log.e("ImageWallpaperGifRenderer", "drawGif: Could not unlock surface.");
                return;
            }
        }
        Log.e("ImageWallpaperGifRenderer", "drawGif :  incorrect size w = " + this.mMovie.width() + " , h = " + this.mMovie.height());
    }

    public final void setInputStreamToMovie(InputStream inputStream) {
        try {
            try {
                try {
                    if (inputStream == null) {
                        Log.e("ImageWallpaperGifRenderer", " is is null");
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                                return;
                            } catch (IOException e) {
                                e.printStackTrace();
                                return;
                            }
                        }
                        return;
                    }
                    Movie decodeStream = Movie.decodeStream(inputStream);
                    this.mMovie = decodeStream;
                    if (decodeStream != null) {
                        Log.i("ImageWallpaperGifRenderer", " movie size : w: " + this.mMovie.width() + " , h: " + this.mMovie.height());
                    } else {
                        Log.i("ImageWallpaperGifRenderer", " movie is null");
                    }
                    inputStream.close();
                } catch (Throwable th) {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Exception e3) {
                e3.printStackTrace();
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        } catch (IOException e4) {
            e4.printStackTrace();
        }
    }

    public final void setMediaPath(String str) {
        try {
            setInputStreamToMovie(new FileInputStream(new File(str)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void setThumbnail(Bitmap bitmap, SurfaceHolder surfaceHolder) {
        if (surfaceHolder != null) {
            Canvas lockCanvas = surfaceHolder.lockCanvas();
            if (lockCanvas == null) {
                Log.e("ImageWallpaperGifRenderer", "Cannot draw onto the canvas as it's null");
                return;
            }
            try {
                adjustCenterCropScale(this.mBoundRect, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()));
                float f = this.mCenterCropScale;
                lockCanvas.scale(f, f, 0.0f, 0.0f);
                PointF pointF = this.mCenterCropOffset;
                lockCanvas.drawBitmap(bitmap, pointF.x, pointF.y, this.mMoviePaint);
            } catch (Exception e) {
                Log.i("ImageWallpaperGifRenderer", e.getMessage());
            }
            try {
                surfaceHolder.unlockCanvasAndPost(lockCanvas);
            } catch (IllegalArgumentException | IllegalStateException unused) {
                Log.e("ImageWallpaperGifRenderer", "setThumbnail: Could not unlock surface.");
            }
        }
    }

    public final void start() {
        NotificationListener$$ExternalSyntheticOutline0.m(new StringBuilder("start, mIsPaused = "), this.mIsPaused, "ImageWallpaperGifRenderer");
        this.mStartedMovieTime = 0L;
        this.mIsFinishedPlayGif = false;
        if (this.mIsPaused) {
            drawGif(this.mSurfaceHolder, false);
        } else {
            sendEmptyMessageDelayed(1001, 50L);
        }
    }

    public final void stop() {
        NotificationListener$$ExternalSyntheticOutline0.m(new StringBuilder("stop, mIsPaused = "), this.mIsPaused, "ImageWallpaperGifRenderer");
        this.mIsFinishedPlayGif = true;
    }

    public final void updateGif(SurfaceHolder surfaceHolder) {
        Log.i("ImageWallpaperGifRenderer", " updateGif true");
        this.mStartedMovieTime = 0L;
        this.mSurfaceHolder = surfaceHolder;
        if (this.mMovie == null) {
            Log.e("ImageWallpaperGifRenderer", "updateGif : movie is NULL");
        } else {
            adjustCenterCropScale(this.mBoundRect, new Rect(0, 0, this.mMovie.width(), this.mMovie.height()));
            drawGif(this.mSurfaceHolder, true);
        }
    }
}
