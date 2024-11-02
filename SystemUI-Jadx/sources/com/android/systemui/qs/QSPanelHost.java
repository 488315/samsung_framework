package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.util.ToBooleanFunction;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.plugins.qs.SQSTile;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.SecQSPanel;
import com.android.systemui.qs.SecQSPanelControllerBase;
import com.android.systemui.qs.bar.BarController;
import com.android.systemui.qs.bar.BarItemImpl;
import com.android.systemui.qs.bar.BottomLargeTileBar;
import com.android.systemui.qs.bar.BrightnessBar;
import com.android.systemui.qs.bar.TopLargeTileBar;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class QSPanelHost implements QSHost.Callback {
    public BarController mBarController;
    public final ArrayList mCallbacks;
    public final MetricsLogger mMetricsLogger;
    public final QSHost mQsHost;
    public final ArrayList mRecords;
    public final SecQSPanelResourcePicker mResourcePicker;
    public final SecQSPanel mTargetView;
    public Function mTileCallbackFunction;
    public SecQSPanel.QSTileLayout mTileLayout;
    public final int mType;

    public QSPanelHost(int i, View view, QSHost qSHost, MetricsLogger metricsLogger, SecQSPanelResourcePicker secQSPanelResourcePicker) {
        this.mType = i;
        this.mTargetView = (SecQSPanel) view;
        this.mQsHost = qSHost;
        this.mResourcePicker = secQSPanelResourcePicker;
        if (isHeader()) {
            qSHost.getQQSTileHost().mCallbacks.add(this);
        } else {
            qSHost.addCallback(this);
        }
        this.mMetricsLogger = metricsLogger;
        this.mRecords = new ArrayList();
        this.mCallbacks = new ArrayList();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v1, types: [com.android.systemui.qs.QSPanelHost$$ExternalSyntheticLambda7] */
    /* JADX WARN: Type inference failed for: r7v2, types: [com.android.systemui.qs.QSPanelHost$$ExternalSyntheticLambda7] */
    /* JADX WARN: Type inference failed for: r7v3, types: [com.android.systemui.qs.QSPanelHost$$ExternalSyntheticLambda7] */
    public final void addBarTiles(int i) {
        ArrayList arrayList = new ArrayList();
        SecQSPanel secQSPanel = this.mTargetView;
        final int i2 = 1;
        final int i3 = 0;
        this.mQsHost.getBarTilesByType(i, secQSPanel.getContext()).stream().map(new QSPanelHost$$ExternalSyntheticLambda4(this, i2)).forEach(new QSPanelHost$$ExternalSyntheticLambda6(0, this, arrayList));
        StringBuilder sb = new StringBuilder("addBarTiles: type=");
        sb.append(i);
        sb.append(" tileRecords=");
        sb.append(arrayList);
        sb.append(" orientation=");
        RecyclerView$$ExternalSyntheticOutline0.m(sb, secQSPanel.getContext().getResources().getConfiguration().orientation, "QSPanelHost");
        if (i != 0) {
            if (i != 1) {
                final int i4 = 2;
                if (i == 2) {
                    final Class<BottomLargeTileBar> cls = BottomLargeTileBar.class;
                    addTilesToBar(arrayList, new ToBooleanFunction() { // from class: com.android.systemui.qs.QSPanelHost$$ExternalSyntheticLambda7
                        public final boolean apply(Object obj) {
                            switch (i4) {
                                case 0:
                                case 1:
                                default:
                                    return cls.isInstance((BarItemImpl) obj);
                            }
                        }
                    }, "BottomBar");
                    return;
                }
                return;
            }
            final Class<BrightnessBar> cls2 = BrightnessBar.class;
            addTilesToBar(arrayList, new ToBooleanFunction() { // from class: com.android.systemui.qs.QSPanelHost$$ExternalSyntheticLambda7
                public final boolean apply(Object obj) {
                    switch (i2) {
                        case 0:
                        case 1:
                        default:
                            return cls2.isInstance((BarItemImpl) obj);
                    }
                }
            }, "BrightnessBar");
            return;
        }
        final Class<TopLargeTileBar> cls3 = TopLargeTileBar.class;
        addTilesToBar(arrayList, new ToBooleanFunction() { // from class: com.android.systemui.qs.QSPanelHost$$ExternalSyntheticLambda7
            public final boolean apply(Object obj) {
                switch (i3) {
                    case 0:
                    case 1:
                    default:
                        return cls3.isInstance((BarItemImpl) obj);
                }
            }
        }, "TopBar");
    }

    public final void addCallbackAndInit(SecQSPanelControllerBase.TileRecord tileRecord) {
        Function function = this.mTileCallbackFunction;
        if (function == null) {
            return;
        }
        SQSTile.SCallback sCallback = (SQSTile.SCallback) function.apply(tileRecord);
        tileRecord.tile.addCallback(sCallback);
        tileRecord.callback = sCallback;
        QSTileView qSTileView = tileRecord.tileView;
        QSTile qSTile = tileRecord.tile;
        qSTileView.init(qSTile);
        qSTile.refreshState();
    }

    public final void addTilesToBar(List list, QSPanelHost$$ExternalSyntheticLambda7 qSPanelHost$$ExternalSyntheticLambda7, String str) {
        getBarItems().stream().filter(new QSPanelHost$$ExternalSyntheticLambda8(qSPanelHost$$ExternalSyntheticLambda7, 0)).map(new QSPanelHost$$ExternalSyntheticLambda0(6)).forEach(new QSPanelHost$$ExternalSyntheticLambda9(this, list, str));
    }

    public final ArrayList getBarItems() {
        if (this.mBarController == null) {
            Log.e("QSPanelHost", "getBarItems: mBarController is null");
            return new ArrayList();
        }
        if (isHeader()) {
            return this.mBarController.mCollapsedBarItems;
        }
        return this.mBarController.mExpandedBarItems;
    }

    public final boolean isHeader() {
        if (this.mType == 1) {
            return true;
        }
        return false;
    }

    @Override // com.android.systemui.qs.QSHost.Callback
    public final void onTilesChanged() {
        setTiles();
        this.mCallbacks.forEach(new QSPanelHost$$ExternalSyntheticLambda1(2));
    }

    public final void setTiles() {
        List arrayList;
        Collection tiles;
        int integer;
        Collection tiles2;
        boolean isHeader = isHeader();
        QSHost qSHost = this.mQsHost;
        SecQSPanel secQSPanel = this.mTargetView;
        if (isHeader) {
            Context context = secQSPanel.getContext();
            this.mResourcePicker.getClass();
            Resources resources = context.getResources();
            if (QpRune.QUICK_TABLET) {
                integer = resources.getInteger(R.integer.sec_quick_qs_panel_max_columns_tablet);
            } else {
                integer = resources.getInteger(R.integer.sec_quick_qs_panel_max_columns);
            }
            if (isHeader()) {
                tiles2 = qSHost.getQQSTileHost().mTiles.values();
            } else {
                tiles2 = qSHost.getTiles();
            }
            arrayList = (List) tiles2.stream().limit(integer).collect(Collectors.toList());
        } else {
            if (isHeader()) {
                tiles = qSHost.getQQSTileHost().mTiles.values();
            } else {
                tiles = qSHost.getTiles();
            }
            arrayList = new ArrayList(tiles);
        }
        ArrayList arrayList2 = this.mRecords;
        arrayList2.forEach(new QSPanelHost$$ExternalSyntheticLambda2(this, 1));
        arrayList2.clear();
        int i = 0;
        if (this.mType == 0) {
            int i2 = secQSPanel.getContext().getResources().getConfiguration().orientation;
            addBarTiles(0);
            addBarTiles(1);
            addBarTiles(2);
            if (i2 != secQSPanel.getContext().getResources().getConfiguration().orientation) {
                Log.d("QSPanelHost", "setTiles : orientation value is changed during updating BarTiles, repeat addBarTiles");
                addBarTiles(0);
                addBarTiles(2);
            }
            getBarItems().stream().filter(new QSPanelHost$$ExternalSyntheticLambda3(1)).forEach(new QSPanelHost$$ExternalSyntheticLambda2(arrayList, 2));
        }
        arrayList.stream().map(new QSPanelHost$$ExternalSyntheticLambda4(this, i)).forEach(new QSPanelHost$$ExternalSyntheticLambda2(arrayList2, 3));
    }
}
