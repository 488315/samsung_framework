package com.android.systemui.qs;

import android.content.ComponentName;
import android.content.Context;
import android.os.Trace;
import android.provider.Settings;
import android.util.Log;
import com.android.systemui.Prefs;
import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.settings.UserTrackerImpl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Provider;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class QSTileHost$$ExternalSyntheticLambda7 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ QSTileHost f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ QSTileHost$$ExternalSyntheticLambda7(QSTileHost qSTileHost, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = qSTileHost;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        final int indexOf;
        switch (this.$r8$classId) {
            case 0:
                QSTileHost qSTileHost = this.f$0;
                Provider provider = (Provider) this.f$1;
                qSTileHost.getClass();
                Trace.traceBegin(64L, "addTunable");
                try {
                    Log.d("QSTileHost", "start addTunable");
                    qSTileHost.initTunerServiceAndAutoTile(provider);
                    return;
                } finally {
                    Trace.traceEnd(64L);
                }
            case 1:
                QSTileHost qSTileHost2 = this.f$0;
                Collection collection = (Collection) this.f$1;
                qSTileHost2.getClass();
                qSTileHost2.changeTileSpecs(new QSTileHost$$ExternalSyntheticLambda14(collection, 1));
                return;
            default:
                final QSTileHost qSTileHost3 = this.f$0;
                ComponentName componentName = (ComponentName) this.f$1;
                if (componentName != null) {
                    Context context = qSTileHost3.mContext;
                    List loadTileSpecs = qSTileHost3.loadTileSpecs(context, Settings.Secure.getStringForUser(context.getContentResolver(), "sysui_qs_tiles", ((UserTrackerImpl) qSTileHost3.mUserTracker).getUserId()));
                    ArrayList arrayList = new ArrayList(loadTileSpecs);
                    String spec = CustomTile.toSpec(componentName);
                    if ("WifiCalling".equals(qSTileHost3.getCustomTileNameFromSpec(spec)) && (indexOf = loadTileSpecs.indexOf(spec)) != -1) {
                        qSTileHost3.mHandler.post(new Runnable() { // from class: com.android.systemui.qs.QSTileHost.5
                            @Override // java.lang.Runnable
                            public final void run() {
                                Prefs.putInt(QSTileHost.this.mContext, "QsWifiCallingTileIndex", indexOf);
                            }
                        });
                        qSTileHost3.mTileIsRemovedByApi = true;
                    }
                    if (arrayList.remove(spec)) {
                        qSTileHost3.changeTilesByUser(loadTileSpecs, arrayList);
                        return;
                    }
                    return;
                }
                qSTileHost3.getClass();
                return;
        }
    }
}
