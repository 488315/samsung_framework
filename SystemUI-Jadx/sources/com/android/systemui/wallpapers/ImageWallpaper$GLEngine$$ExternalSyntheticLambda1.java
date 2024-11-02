package com.android.systemui.wallpapers;

import android.opengl.GLES20;
import com.android.systemui.LsRune;
import com.android.systemui.wallpapers.ImageWallpaper;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class ImageWallpaper$GLEngine$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ImageWallpaper.BaseEngine f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ ImageWallpaper$GLEngine$$ExternalSyntheticLambda1(ImageWallpaper.BaseEngine baseEngine, int i, int i2, int i3) {
        this.$r8$classId = i3;
        this.f$0 = baseEngine;
        this.f$1 = i;
        this.f$2 = i2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ImageWallpaper.GLEngine gLEngine = (ImageWallpaper.GLEngine) this.f$0;
                int i = this.f$1;
                int i2 = this.f$2;
                gLEngine.getClass();
                if (LsRune.WALLPAPER_SUB_DISPLAY_MODE) {
                    gLEngine.cancelFinishRenderingTask();
                    gLEngine.finishRendering();
                }
                gLEngine.mRenderer.getClass();
                GLES20.glViewport(0, 0, i, i2);
                return;
            default:
                ImageWallpaper.VideoGLEngine videoGLEngine = (ImageWallpaper.VideoGLEngine) this.f$0;
                videoGLEngine.mRenderer.onSurfaceChanged(this.f$1, this.f$2);
                return;
        }
    }
}
