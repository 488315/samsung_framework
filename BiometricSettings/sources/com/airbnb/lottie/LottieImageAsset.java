package com.airbnb.lottie;

import android.graphics.Bitmap;

/* loaded from: classes.dex */
public final class LottieImageAsset {
    private Bitmap bitmap;
    private final String fileName;
    private final int height;
    private final String id;
    private final int width;

    public LottieImageAsset(int i, int i2, String str, String str2) {
        this.width = i;
        this.height = i2;
        this.id = str;
        this.fileName = str2;
    }

    public final Bitmap getBitmap() {
        return this.bitmap;
    }

    public final String getFileName() {
        return this.fileName;
    }

    public final int getHeight() {
        return this.height;
    }

    public final String getId() {
        return this.id;
    }

    public final int getWidth() {
        return this.width;
    }

    public final void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
