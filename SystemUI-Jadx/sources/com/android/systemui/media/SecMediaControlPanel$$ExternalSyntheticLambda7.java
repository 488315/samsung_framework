package com.android.systemui.media;

import android.app.PendingIntent;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.android.systemui.media.controls.models.player.MediaData;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.function.Consumer;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final /* synthetic */ class SecMediaControlPanel$$ExternalSyntheticLambda7 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ SecMediaControlPanel$$ExternalSyntheticLambda7(SecMediaControlPanel secMediaControlPanel, MediaData mediaData, TextView textView) {
        this.$r8$classId = 1;
        this.f$0 = secMediaControlPanel;
        this.f$2 = mediaData;
        this.f$1 = textView;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        switch (this.$r8$classId) {
            case 0:
                SecMediaControlPanel secMediaControlPanel = (SecMediaControlPanel) this.f$0;
                PendingIntent pendingIntent = (PendingIntent) this.f$1;
                MediaData mediaData = (MediaData) this.f$2;
                if (pendingIntent == null) {
                    secMediaControlPanel.getClass();
                    Log.d("MediaControlPanel", "click intent is null");
                    return;
                } else if (secMediaControlPanel.isDisabledPlayer()) {
                    Log.d("MediaControlPanel", "is disabled player");
                    return;
                } else {
                    SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.sCurrentScreenID, "QPNE0002", "app", mediaData.packageName);
                    secMediaControlPanel.mActivityStarter.postStartActivityDismissingKeyguard(pendingIntent, true);
                    return;
                }
            case 1:
                SecMediaControlPanel secMediaControlPanel2 = (SecMediaControlPanel) this.f$0;
                MediaData mediaData2 = (MediaData) this.f$2;
                TextView textView = (TextView) this.f$1;
                secMediaControlPanel2.getClass();
                SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.sCurrentScreenID, "QPNE0018", "app", mediaData2.packageName);
                Log.d("MediaControlPanel", "MEDIA_OUTPUT_OPEN");
                secMediaControlPanel2.mMediaOutputHelper.show(secMediaControlPanel2.mContext, true, secMediaControlPanel2.mType, mediaData2.packageName, textView, secMediaControlPanel2.mToken);
                return;
            case 2:
                SecMediaControlPanel secMediaControlPanel3 = (SecMediaControlPanel) this.f$0;
                View view2 = (View) this.f$1;
                View view3 = (View) this.f$2;
                secMediaControlPanel3.getClass();
                view2.setVisibility(8);
                view3.setVisibility(0);
                secMediaControlPanel3.setBackgroundColor(secMediaControlPanel3.mBackgroundColor);
                return;
            default:
                Consumer consumer = (Consumer) this.f$0;
                ImageButton imageButton = (ImageButton) this.f$1;
                Runnable runnable = (Runnable) this.f$2;
                consumer.accept(imageButton);
                runnable.run();
                return;
        }
    }

    public /* synthetic */ SecMediaControlPanel$$ExternalSyntheticLambda7(Object obj, Object obj2, int i, Object obj3) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
        this.f$2 = obj3;
    }
}
