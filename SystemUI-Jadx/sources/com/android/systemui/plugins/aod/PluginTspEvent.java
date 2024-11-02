package com.android.systemui.plugins.aod;

import java.util.Locale;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public class PluginTspEvent {
    public final int action;
    public final int x;
    public final int y;

    public PluginTspEvent(int i, int i2, int i3) {
        this.action = i;
        this.x = i2;
        this.y = i3;
    }

    public String toString() {
        return String.format(Locale.getDefault(), "[PluginTspEvent : action = %s, x = %d, y = %d]", Integer.valueOf(this.action), Integer.valueOf(this.x), Integer.valueOf(this.y));
    }
}
