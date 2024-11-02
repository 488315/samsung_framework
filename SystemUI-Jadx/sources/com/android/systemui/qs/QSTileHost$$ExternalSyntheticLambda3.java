package com.android.systemui.qs;

import android.util.Log;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.systemui.plugins.qs.QSTile;
import java.util.Map;
import java.util.function.Consumer;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class QSTileHost$$ExternalSyntheticLambda3 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ QSTileHost f$0;

    public /* synthetic */ QSTileHost$$ExternalSyntheticLambda3(QSTileHost qSTileHost, int i) {
        this.$r8$classId = i;
        this.f$0 = qSTileHost;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                QSTileHost qSTileHost = this.f$0;
                Map.Entry entry = (Map.Entry) obj;
                qSTileHost.getClass();
                Log.d("QSTileHost", "Destroying tile: " + ((String) entry.getKey()));
                qSTileHost.mQSLogger.logTileDestroyed((String) entry.getKey(), "Tile removed");
                qSTileHost.mQSTileInstanceManager.releaseTileUsing(qSTileHost.mTileUsingByPanel, ((QSTile) entry.getValue()).getTileSpec());
                return;
            default:
                QSTileHost qSTileHost2 = this.f$0;
                Map.Entry entry2 = (Map.Entry) obj;
                if (QSTileHost.DEBUG) {
                    qSTileHost2.getClass();
                    ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("Destroying tile: "), (String) entry2.getKey(), "QSTileHost");
                }
                qSTileHost2.mQSTileInstanceManager.releaseTileUsing(qSTileHost2.mTileUsingByPanel, ((QSTile) entry2.getValue()).getTileSpec());
                return;
        }
    }
}
