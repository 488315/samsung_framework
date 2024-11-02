package android.inputmethodservice.navigationbar;

import android.content.res.Resources;
import android.util.TypedValue;

/* loaded from: classes2.dex */
final class NavigationBarUtils {
    private NavigationBarUtils() {
    }

    public static int dpToPx(float dpValue, Resources res) {
        return (int) TypedValue.applyDimension(1, dpValue, res.getDisplayMetrics());
    }
}
