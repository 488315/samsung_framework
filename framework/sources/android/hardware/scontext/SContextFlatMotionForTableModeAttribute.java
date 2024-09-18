package android.hardware.scontext;

import android.os.Bundle;
import android.util.Log;

@Deprecated
/* loaded from: classes2.dex */
public class SContextFlatMotionForTableModeAttribute extends SContextAttribute {
    private static final String TAG = "SContextFlatMotionForTableModeAttribute";
    private int mDuration;

    /* JADX INFO: Access modifiers changed from: package-private */
    public SContextFlatMotionForTableModeAttribute() {
        this.mDuration = 500;
        setAttribute();
    }

    public SContextFlatMotionForTableModeAttribute(int duration) {
        this.mDuration = 500;
        this.mDuration = duration;
        setAttribute();
    }

    @Override // android.hardware.scontext.SContextAttribute, com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        if (this.mDuration < 0) {
            Log.e(TAG, "The duration is wrong.");
            return false;
        }
        return true;
    }

    private void setAttribute() {
        Bundle attribute = new Bundle();
        attribute.putInt("duration", this.mDuration);
        super.setAttribute(36, attribute);
    }
}
