package android.inputmethodservice;

import android.content.Context;
import android.util.Log;
import com.samsung.android.content.smartclip.SpenGestureManager;

/* loaded from: classes2.dex */
final class SemSpenGestureManagerWrapper {
    static SpenGestureManager mSpenGestureManager = null;

    SemSpenGestureManagerWrapper() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void notifyKeyboardClosedForAGIF(Context context) {
        if (mSpenGestureManager == null) {
            mSpenGestureManager = (SpenGestureManager) context.getSystemService(Context.SEM_SPEN_GESTURE_SERVICE);
        }
        Log.d("InputMethodService", "notifyKeyboardClosed called.");
        SpenGestureManager spenGestureManager = mSpenGestureManager;
        if (spenGestureManager != null) {
            spenGestureManager.notifyKeyboardClosed();
        }
    }
}