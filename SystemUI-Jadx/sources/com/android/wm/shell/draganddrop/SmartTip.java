package com.android.wm.shell.draganddrop;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import com.samsung.android.widget.SemTipPopup;
import com.sec.ims.volte2.data.VolteConstants;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class SmartTip {
    public final Context mContext;
    public final String mKey;
    public final int mLayoutResId;
    public final int mLimitCount;
    public final int mMsgResId;
    public final SharedPreferences mPreferences;
    public View mRootView;
    public boolean mShowRequested;
    public SemTipPopup mTipPopup;
    public final String mTitle;
    public final WindowManager mWindowManager;

    public SmartTip(Context context, String str, String str2, String str3, int i, int i2, int i3) {
        Context createWindowContext = context.createWindowContext(VolteConstants.ErrorCode.DIAL_ALTERNATIVE_NUMBER, null);
        this.mContext = createWindowContext;
        this.mTitle = str;
        this.mWindowManager = (WindowManager) createWindowContext.getSystemService("window");
        this.mPreferences = createWindowContext.getSharedPreferences(str2, 0);
        this.mKey = str3;
        this.mLimitCount = createWindowContext.getResources().getInteger(i2);
        this.mMsgResId = i;
        this.mLayoutResId = i3;
    }

    public final void showTipPopup(int i, int i2, int i3, boolean z) {
        SemTipPopup semTipPopup = this.mTipPopup;
        if (semTipPopup != null && !semTipPopup.isShowing()) {
            Log.i("SmartTip" + this.mTitle, "show tipPopup");
            this.mTipPopup.setMessage(this.mContext.getResources().getString(this.mMsgResId));
            this.mTipPopup.setExpanded(z);
            this.mTipPopup.setTargetPosition(i, i2);
            this.mTipPopup.show(i3);
        }
    }
}
