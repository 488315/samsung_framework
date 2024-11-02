package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.view.View;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.qs.SecQSPanel;
import com.android.systemui.qs.bar.BarController;
import com.android.systemui.qs.bar.BarController$$ExternalSyntheticLambda5;
import com.android.systemui.qs.bar.BarController$$ExternalSyntheticLambda6;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.util.ConfigurationState;
import java.util.Arrays;
import java.util.function.DoubleSupplier;
import javax.inject.Provider;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class SecQuickQSPanelController extends SecQSPanelControllerBase {
    public View mBrightnessMediaDeviceBar;
    public final ConfigurationState mLastConfigurationState;
    public View mMediaPlayerBar;
    public int mOrientation;

    public SecQuickQSPanelController(SecQuickQSPanel secQuickQSPanel, QSHost qSHost, MetricsLogger metricsLogger, UiEventLogger uiEventLogger, QSLogger qSLogger, DumpManager dumpManager, QSPanelHost qSPanelHost, Provider provider, final SecQSPanelResourcePicker secQSPanelResourcePicker) {
        super(secQuickQSPanel, qSHost, metricsLogger, uiEventLogger, qSLogger, dumpManager, qSPanelHost, (BarController) provider.get(), secQSPanelResourcePicker);
        this.mLastConfigurationState = new ConfigurationState(Arrays.asList(ConfigurationState.ConfigurationField.ORIENTATION, ConfigurationState.ConfigurationField.SCREEN_HEIGHT_DP, ConfigurationState.ConfigurationField.SCREEN_LAYOUT));
        final BarController barController = (BarController) provider.get();
        ((SecQuickQSPanel) this.mView).mMeasuredHeightSupplier = new DoubleSupplier() { // from class: com.android.systemui.qs.SecQuickQSPanelController$$ExternalSyntheticLambda0
            @Override // java.util.function.DoubleSupplier
            public final double getAsDouble() {
                SecQuickQSPanelController secQuickQSPanelController = SecQuickQSPanelController.this;
                SecQSPanelResourcePicker secQSPanelResourcePicker2 = secQSPanelResourcePicker;
                BarController barController2 = barController;
                int measuredHeight = ((View) secQuickQSPanelController.mTileLayout).getMeasuredHeight();
                Context context = ((SecQuickQSPanel) secQuickQSPanelController.mView).getContext();
                secQSPanelResourcePicker2.getClass();
                int quickQSCommonBottomMargin = SecQSPanelResourcePicker.getQuickQSCommonBottomMargin(context) + measuredHeight;
                int i = 0;
                if (barController2 != null) {
                    i = Math.max(0, barController2.mCollapsedBarItems.parallelStream().filter(new BarController$$ExternalSyntheticLambda5(0)).mapToInt(new BarController$$ExternalSyntheticLambda6()).sum());
                }
                return quickQSCommonBottomMargin + i;
            }
        };
    }

    @Override // com.android.systemui.qs.SecQSPanelControllerBase
    public final SecQSPanel.QSTileLayout getOrCreateTileLayout() {
        return new SecHeaderTileLayout(((SecQuickQSPanel) this.mView).getContext());
    }

    @Override // com.android.systemui.qs.SecQSPanelControllerBase
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int i = ((SecQuickQSPanel) this.mView).getContext().getResources().getConfiguration().orientation;
        RecyclerView$$ExternalSyntheticOutline0.m(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("onConfigurationChanged currentOrientation = ", i, ",newConfig.orientation = "), configuration.orientation, "SecQuickQSPanelController");
        ConfigurationState configurationState = this.mLastConfigurationState;
        if (configurationState.needToUpdate(configuration) || this.mOrientation != i) {
            this.mOrientation = i;
            this.mQsPanelHost.setTiles();
            configurationState.update(configuration);
        }
    }

    @Override // com.android.systemui.qs.SecQSPanelControllerBase, com.android.systemui.util.ViewController
    public final void onViewAttached() {
        super.onViewAttached();
        this.mBrightnessMediaDeviceBar = ((SecQuickQSPanel) this.mView).findViewById(R.id.brightness_media_device_bar);
        this.mMediaPlayerBar = ((SecQuickQSPanel) this.mView).findViewById(R.id.media_player_root_view);
        updatePaddingAndMargins();
    }

    @Override // com.android.systemui.qs.SecQSPanelControllerBase
    public final void setExpanded(boolean z) {
        int i;
        super.setExpanded(z);
        SecQuickQSPanel secQuickQSPanel = (SecQuickQSPanel) this.mView;
        if (z) {
            i = 4;
        } else {
            i = 0;
        }
        secQuickQSPanel.setVisibility(i);
    }

    @Override // com.android.systemui.qs.SecQSPanelControllerBase
    public final void updatePaddingAndMargins() {
        boolean z;
        int i;
        if (this.mView == null) {
            return;
        }
        Context context = getContext();
        this.mResourcePicker.getClass();
        int qQSPanelSidePadding = SecQSPanelResourcePicker.getQQSPanelSidePadding(context);
        SecQuickQSPanel secQuickQSPanel = (SecQuickQSPanel) this.mView;
        int i2 = 0;
        secQuickQSPanel.setPadding(qQSPanelSidePadding, secQuickQSPanel.getPaddingTop(), qQSPanelSidePadding, 0);
        int panelStartEndPadding = SecQSPanelResourcePicker.getPanelStartEndPadding(context);
        SecQSPanel.QSTileLayout qSTileLayout = this.mTileLayout;
        if (qSTileLayout != null) {
            int i3 = panelStartEndPadding + 0;
            SecHeaderTileLayout secHeaderTileLayout = (SecHeaderTileLayout) qSTileLayout;
            secHeaderTileLayout.setPadding(i3, secHeaderTileLayout.getPaddingTop(), i3, 0);
        }
        View view = this.mBrightnessMediaDeviceBar;
        if (view != null) {
            view.setPadding(panelStartEndPadding, view.getPaddingTop(), panelStartEndPadding, this.mBrightnessMediaDeviceBar.getPaddingBottom());
        }
        if (this.mMediaPlayerBar != null) {
            Resources resources = context.getResources();
            if (QpRune.QUICK_TABLET) {
                i = context.getResources().getDimensionPixelSize(R.dimen.sec_media_player_side_padding_tablet);
            } else {
                if (resources.getConfiguration().orientation == 2) {
                    z = true;
                } else {
                    z = false;
                }
                if (!z) {
                    i2 = SecQSPanelResourcePicker.getNotificationSidePadding(context);
                }
                i = i2;
            }
            View view2 = this.mMediaPlayerBar;
            view2.setPadding(i, view2.getPaddingTop(), i, this.mMediaPlayerBar.getPaddingBottom());
        }
    }

    @Override // com.android.systemui.qs.SecQSPanelControllerBase
    public final void updatePanelContents() {
        LinearLayout linearLayout = new LinearLayout(((SecQuickQSPanel) this.mView).getContext());
        linearLayout.setTag("qqs_expand_anim");
        linearLayout.addView((View) this.mTileLayout);
        ((SecQuickQSPanel) this.mView).addView(linearLayout);
        addBarItems();
    }
}
