package com.android.wm.shell.sysui;

import android.content.res.Configuration;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public interface ConfigurationChangeListener {
    void onConfigurationChanged(Configuration configuration);

    default void onDensityOrFontScaleChanged$1() {
    }

    default void onThemeChanged() {
    }
}
