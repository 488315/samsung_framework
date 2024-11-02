package com.android.systemui.wallpaper.theme.particle;

import android.content.Context;
import com.android.systemui.wallpaper.theme.DensityUtil;
import java.util.Random;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class Rain {
    public static final double[] speed = {0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.1d, 0.1d, 0.2d, 0.2d, 0.3d, 0.3d, 0.3d, 0.5d, 0.5d, 0.5d, 0.5d};
    public final Random mRandom;
    public final double mXSpeed;
    public final int mYSpeed;
    public float x;
    public float y;

    public Rain(Context context) {
        Random random = new Random();
        this.mRandom = random;
        this.x = random.nextInt(DensityUtil.sMetricsWidth - 32) + 16;
        this.y = random.nextInt(DensityUtil.sMetricsHeight);
        this.mXSpeed = speed[random.nextInt(16)];
        this.mYSpeed = random.nextInt(2) + 6;
    }
}
