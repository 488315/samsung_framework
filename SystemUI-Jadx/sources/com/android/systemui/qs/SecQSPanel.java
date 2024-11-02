package com.android.systemui.qs;

import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Debug;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.secutil.Log;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.LinearLayout;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R;
import com.android.systemui.qs.SecQSPanel;
import com.android.systemui.qs.SecQSPanelControllerBase;
import com.android.systemui.qs.logging.QSLogger;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public class SecQSPanel extends LinearLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Runnable mCollapseExpandAction;
    public final Context mContext;
    public final List mOnConfigurationChangedListeners;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public interface OnConfigurationChangedListener {
        void onConfigurationChange(Configuration configuration);
    }

    public SecQSPanel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mOnConfigurationChangedListeners = new ArrayList();
        this.mContext = context;
        setOrientation(1);
        updateDetailColor();
    }

    public QSEvent closePanelEvent() {
        return QSEvent.QS_PANEL_COLLAPSED;
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        super.draw(canvas);
    }

    public String getDumpableTag() {
        return "SecQSPanel";
    }

    @Override // android.view.View
    public final void onConfigurationChanged(final Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ((ArrayList) this.mOnConfigurationChangedListeners).forEach(new Consumer() { // from class: com.android.systemui.qs.SecQSPanel$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Configuration configuration2 = configuration;
                int i = SecQSPanel.$r8$clinit;
                ((SecQSPanel.OnConfigurationChangedListener) obj).onConfigurationChange(configuration2);
            }
        });
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_COLLAPSE);
    }

    public QSEvent openPanelEvent() {
        return QSEvent.QS_PANEL_EXPANDED;
    }

    @Override // android.view.View
    public final boolean performAccessibilityAction(int i, Bundle bundle) {
        Runnable runnable;
        if ((i == 262144 || i == 524288) && (runnable = this.mCollapseExpandAction) != null) {
            runnable.run();
            return true;
        }
        return super.performAccessibilityAction(i, bundle);
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        super.setVisibility(i);
        Log.d("SecQSPanel", "setVisibility " + Debug.getCallers(5, " - "));
    }

    public final void updateDetailColor() {
        int color = this.mContext.getColor(R.color.qs_detail_item_name);
        int color2 = this.mContext.getColor(R.color.sec_qs_detail_description_text_color);
        ContentResolver contentResolver = this.mContext.getContentResolver();
        int currentUser = ActivityManager.getCurrentUser();
        Settings.System.putIntForUser(contentResolver, "qs_detail_content_primary_text_color", color, currentUser);
        Settings.System.putIntForUser(contentResolver, "qs_detail_content_secondary_text_color", color2, currentUser);
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public interface QSTileLayout {
        void addTile(SecQSPanelControllerBase.TileRecord tileRecord);

        void removeTile(SecQSPanelControllerBase.TileRecord tileRecord);

        void setListening(boolean z, UiEventLogger uiEventLogger);

        boolean updateResources();

        default void restoreInstanceState(Bundle bundle) {
        }

        default void saveInstanceState(Bundle bundle) {
        }

        default void setExpansion(float f) {
        }

        default void setLogger(QSLogger qSLogger) {
        }
    }
}
