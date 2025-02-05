package com.sec.internal.ims.util;

import android.content.Context;
import android.view.WindowManager;
import com.sec.internal.ims.servicemodules.csh.event.VshOrientation;

/* loaded from: classes.dex */
public class DeviceOrientationStatus {
    private static final int FLIPPED_LANDSCAPE = 3;
    private static final int LANDSCAPE = 1;
    private static final int PORTRAIT = 0;
    private static final int REVERSE_PORTRAIT = 2;

    public static VshOrientation getDeviceOrientation(Context context) {
        return translate(((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRotation());
    }

    public static VshOrientation translate(int i) {
        if (i == 0) {
            return VshOrientation.PORTRAIT;
        }
        if (i == 1) {
            return VshOrientation.LANDSCAPE;
        }
        if (i == 2) {
            return VshOrientation.REVERSE_PORTRAIT;
        }
        if (i == 3) {
            return VshOrientation.FLIPPED_LANDSCAPE;
        }
        return VshOrientation.PORTRAIT;
    }
}
