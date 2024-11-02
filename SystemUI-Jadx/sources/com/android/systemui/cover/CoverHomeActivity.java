package com.android.systemui.cover;

import android.app.Activity;
import android.content.res.Configuration;
import android.util.Log;
import android.view.KeyEvent;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.plugins.cover.PluginCover;
import com.android.systemui.plugins.cover.PluginDisplayCover;
import dagger.Lazy;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public class CoverHomeActivity extends Activity {
    public final Lazy mCoverScreenManagerLazy;

    public CoverHomeActivity(Lazy lazy) {
        Log.d("CoverHomeActivity", "CoverHomeActivity() ");
        this.mCoverScreenManagerLazy = lazy;
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Log.d("CoverHomeActivity", "onConfigurationChanged() ");
        PluginCover pluginCover = ((CoverScreenManager) this.mCoverScreenManagerLazy.get()).mCoverPlugin;
        if (pluginCover != null && (pluginCover instanceof PluginDisplayCover)) {
            pluginCover.onConfigurationChanged(configuration);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:6:0x0056  */
    @Override // android.app.Activity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onCreate(android.os.Bundle r7) {
        /*
            Method dump skipped, instructions count: 264
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.cover.CoverHomeActivity.onCreate(android.os.Bundle):void");
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
        Log.d("CoverHomeActivity", "onDestroy() ");
        CoverScreenManager coverScreenManager = (CoverScreenManager) this.mCoverScreenManagerLazy.get();
        CoverHomeActivity coverHomeActivity = coverScreenManager.mActivity;
        if (coverHomeActivity == null) {
            Log.d("CoverScreenManager", "clearCoverHomeActivity() already activity is null");
            return;
        }
        if (!coverHomeActivity.equals(this)) {
            Log.d("CoverScreenManager", "clearCoverHomeActivity() already exists activity - " + coverScreenManager.mActivity);
        } else {
            coverScreenManager.mActivity = null;
            coverScreenManager.updatePluginListener();
        }
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4) {
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("onWindowFocusChanged() hasFocus=", z, "CoverHomeActivity");
        PluginCover pluginCover = ((CoverScreenManager) this.mCoverScreenManagerLazy.get()).mCoverPlugin;
        if (pluginCover instanceof PluginDisplayCover) {
            ((PluginDisplayCover) pluginCover).onWindowFocusChanged(z);
        }
    }
}
