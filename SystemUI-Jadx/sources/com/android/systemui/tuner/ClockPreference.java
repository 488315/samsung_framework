package com.android.systemui.tuner;

import android.content.Context;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.AttributeSet;
import androidx.preference.DropDownPreference;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.tuner.TunerService;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public class ClockPreference extends DropDownPreference implements TunerService.Tunable {
    public final String mClock;
    public boolean mClockEnabled;
    public boolean mHasSeconds;
    public boolean mHasSetValue;
    public ArraySet mHideList;
    public boolean mReceivedClock;
    public boolean mReceivedSeconds;

    public ClockPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mClock = context.getString(17042915);
        this.mEntryValues = new CharSequence[]{"seconds", "default", "disabled"};
    }

    @Override // androidx.preference.Preference
    public final void onAttached() {
        super.onAttached();
        ((TunerService) Dependency.get(TunerService.class)).addTunable(this, "icon_blacklist", "clock_seconds");
    }

    @Override // androidx.preference.Preference
    public final void onDetached() {
        ((TunerService) Dependency.get(TunerService.class)).removeTunable(this);
        unregisterDependency();
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public final void onTuningChanged(String str, String str2) {
        boolean z;
        if ("icon_blacklist".equals(str)) {
            this.mReceivedClock = true;
            this.mHideList = StatusBarIconController.getIconHideList(this.mContext, str2);
            this.mClockEnabled = !r3.contains(this.mClock);
        } else if ("clock_seconds".equals(str)) {
            this.mReceivedSeconds = true;
            if (str2 != null && Integer.parseInt(str2) != 0) {
                z = true;
            } else {
                z = false;
            }
            this.mHasSeconds = z;
        }
        if (!this.mHasSetValue && this.mReceivedClock && this.mReceivedSeconds) {
            this.mHasSetValue = true;
            boolean z2 = this.mClockEnabled;
            if (z2 && this.mHasSeconds) {
                setValue("seconds");
            } else if (z2) {
                setValue("default");
            } else {
                setValue("disabled");
            }
        }
    }

    @Override // androidx.preference.Preference
    public final void persistString(String str) {
        ((TunerService) Dependency.get(TunerService.class)).setValue("seconds".equals(str) ? 1 : 0, "clock_seconds");
        if ("disabled".equals(str)) {
            this.mHideList.add(this.mClock);
        } else {
            this.mHideList.remove(this.mClock);
        }
        ((TunerService) Dependency.get(TunerService.class)).setValue("icon_blacklist", TextUtils.join(",", this.mHideList));
    }
}
