package com.android.wm.shell.pip.tv;

import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class TvPipInterpolators {
    public static final Interpolator BROWSE;
    public static final Interpolator ENTER;
    public static final Interpolator EXIT;

    static {
        new PathInterpolator(0.2f, 0.1f, 0.0f, 1.0f);
        BROWSE = new PathInterpolator(0.18f, 1.0f, 0.22f, 1.0f);
        ENTER = new PathInterpolator(0.12f, 1.0f, 0.4f, 1.0f);
        EXIT = new PathInterpolator(0.4f, 1.0f, 0.12f, 1.0f);
    }
}
