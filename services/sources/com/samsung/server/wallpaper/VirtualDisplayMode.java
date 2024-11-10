package com.samsung.server.wallpaper;

import android.hardware.display.DisplayManager;
import android.view.Display;

/* loaded from: classes2.dex */
public class VirtualDisplayMode {
    public final DisplayManager mDisplayManager;

    public VirtualDisplayMode(DisplayManager displayManager) {
        this.mDisplayManager = displayManager;
    }

    public boolean isVirtualWallpaperDisplay(int i) {
        Display[] displays = this.mDisplayManager.getDisplays("com.samsung.android.hardware.display.category.VIEW_COVER_DISPLAY");
        if (displays.length > 0) {
            for (Display display : displays) {
                if (i == display.getDisplayId()) {
                    return true;
                }
            }
        }
        return false;
    }
}
