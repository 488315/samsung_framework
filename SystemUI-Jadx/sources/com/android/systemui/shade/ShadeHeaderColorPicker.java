package com.android.systemui.shade;

import android.content.Context;
import android.view.ContextThemeWrapper;
import com.android.systemui.DualToneHandler;
import com.android.systemui.R;
import com.android.systemui.util.DeviceState;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class ShadeHeaderColorPicker {
    public float colorIntensity;
    public Context context;
    public final DualToneHandler dualToneHandler;

    public ShadeHeaderColorPicker(Context context) {
        this.context = context;
        update(context);
        this.dualToneHandler = new DualToneHandler(new ContextThemeWrapper(this.context, 2132018544));
    }

    public final int getClockColor() {
        boolean z;
        if (DeviceState.isOpenTheme(this.context)) {
            if (this.colorIntensity == 1.0f) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                return this.context.getColor(R.color.shade_header_clock_color);
            }
            return this.context.getColor(R.color.shade_header_clock_color_dark);
        }
        return this.context.getColor(R.color.status_bar_clock_color);
    }

    public final void update(Context context) {
        float f;
        this.context = context;
        if (DeviceState.isOpenTheme(context)) {
            int color = this.context.getColor(R.color.sec_qs_header_tint_color);
            int i = (color >> 16) & 255;
            int i2 = (color >> 8) & 255;
            int i3 = color & 255;
            if (((int) Math.sqrt((i3 * i3 * 0.068d) + (i2 * i2 * 0.691d) + (i * i * 0.241d))) < 50) {
                f = 1.0f;
                this.colorIntensity = f;
            }
        }
        f = 0.0f;
        this.colorIntensity = f;
    }
}
