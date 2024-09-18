package android.hardware.scontext;

import android.media.tv.TvContract;
import android.os.Bundle;
import android.util.Log;

@Deprecated
/* loaded from: classes2.dex */
public class SContextInactiveTimerAttribute extends SContextAttribute {
    private static final String TAG = "SContextInactiveTimerAttribute";
    private int mAlertCount;
    private int mDeviceType;
    private int mDuration;
    private int mEndTime;
    private int mStartTime;

    /* JADX INFO: Access modifiers changed from: package-private */
    public SContextInactiveTimerAttribute() {
        this.mDeviceType = 1;
        this.mDuration = 3600;
        this.mAlertCount = 1;
        this.mStartTime = 1500;
        this.mEndTime = 1500;
        setAttribute();
    }

    public SContextInactiveTimerAttribute(int deviceType, int duration, int alertCount, int startTime, int endTime) {
        this.mDeviceType = 1;
        this.mDuration = 3600;
        this.mAlertCount = 1;
        this.mStartTime = 1500;
        this.mEndTime = 1500;
        this.mDeviceType = deviceType;
        this.mDuration = duration;
        this.mAlertCount = alertCount;
        this.mStartTime = startTime;
        this.mEndTime = endTime;
        setAttribute();
    }

    @Override // android.hardware.scontext.SContextAttribute, com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        int i = this.mDeviceType;
        if (i != 1 && i != 2) {
            Log.e(TAG, "The deivce type is wrong.");
            return false;
        }
        if (this.mDuration < 0) {
            Log.e(TAG, "The duration is wrong.");
            return false;
        }
        if (this.mAlertCount < 0) {
            Log.e(TAG, "The alert count is wrong.");
            return false;
        }
        if (this.mStartTime < 0) {
            Log.e(TAG, "The start time is wrong.");
            return false;
        }
        if (this.mEndTime >= 0) {
            return true;
        }
        Log.e(TAG, "The end time is wrong.");
        return false;
    }

    private void setAttribute() {
        Bundle attribute = new Bundle();
        attribute.putInt("device_type", this.mDeviceType);
        attribute.putInt("duration", this.mDuration);
        attribute.putInt("alert_count", this.mAlertCount);
        attribute.putInt(TvContract.PARAM_START_TIME, this.mStartTime);
        attribute.putInt(TvContract.PARAM_END_TIME, this.mEndTime);
        super.setAttribute(35, attribute);
    }
}
