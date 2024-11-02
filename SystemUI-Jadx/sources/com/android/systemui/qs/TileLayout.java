package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Resources;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.TextView;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.qs.SecQSPanel;
import com.android.systemui.qs.SecQSPanelControllerBase;
import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.Utils;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.IntFunction;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public class TileLayout extends ViewGroup implements SecQSPanel.QSTileLayout {
    public int mCellHeight;
    public int mCellMarginHorizontal;
    public int mCellMarginVertical;
    public int mCellWidth;
    public int mColumns;
    public int mLastCellWidth;
    public boolean mListening;
    public int mMaxAllowedRows;
    public int mMaxCellHeight;
    public final int mMinRows;
    public final ArrayList mRecords;
    public int mRows;
    public final SecTileLayout mSecTileLayout;
    public int mSidePadding;

    public TileLayout(Context context) {
        this(context, null);
    }

    @Override // com.android.systemui.qs.SecQSPanel.QSTileLayout
    public final void addTile(SecQSPanelControllerBase.TileRecord tileRecord) {
        boolean z;
        boolean z2;
        this.mRecords.add(tileRecord);
        boolean z3 = this.mListening;
        QSTile qSTile = tileRecord.tile;
        qSTile.setListening(this, z3);
        addTileView(tileRecord);
        QSTileImpl qSTileImpl = (QSTileImpl) qSTile;
        qSTileImpl.getClass();
        if (qSTileImpl instanceof CustomTile) {
            z = ((CustomTile) qSTileImpl).mIsSecCustomTile;
        } else {
            z = false;
        }
        String str = qSTileImpl.mTileSpec;
        if (str != null) {
            z2 = qSTileImpl.mHost.isAvailableForSearch(str, z);
        } else {
            z2 = false;
        }
        if (z2) {
            qSTileImpl.mHandler.obtainMessage(102, 0, 0).sendToTarget();
        }
    }

    public void addTileView(SecQSPanelControllerBase.TileRecord tileRecord) {
        addView(tileRecord.tileView);
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    public final void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        accessibilityNodeInfo.setCollectionInfo(new AccessibilityNodeInfo.CollectionInfo(this.mRecords.size(), 1, false));
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        boolean z2;
        int i5;
        int size = this.mRecords.size();
        if (getLayoutDirection() == 1) {
            z2 = true;
        } else {
            z2 = false;
        }
        int min = Math.min(size, this.mRows * this.mColumns);
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        while (i6 < min) {
            if (i7 == this.mColumns) {
                i8++;
                i7 = 0;
            }
            SecQSPanelControllerBase.TileRecord tileRecord = (SecQSPanelControllerBase.TileRecord) this.mRecords.get(i6);
            int i9 = this.mCellHeight;
            SecTileLayout secTileLayout = this.mSecTileLayout;
            int i10 = secTileLayout.tileVerticalMargin;
            int i11 = ((i9 + i10) * i8) + i10;
            if (z2) {
                i5 = (this.mColumns - i7) - 1;
            } else {
                i5 = i7;
            }
            int asInt = secTileLayout.sidePaddingSupplier.getAsInt() + ((secTileLayout.cellMarginHorizontalSupplier.getAsInt() + secTileLayout.cellWidthSupplier.getAsInt()) * i5);
            int i12 = this.mCellWidth + asInt;
            int measuredHeight = tileRecord.tileView.getMeasuredHeight() + i11;
            QSTileView qSTileView = tileRecord.tileView;
            qSTileView.layout(asInt, i11, i12, measuredHeight);
            qSTileView.setPosition(i6);
            qSTileView.getMeasuredHeight();
            i6++;
            i7++;
        }
        this.mRecords.size();
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        int tileExpandedHeight;
        int i3;
        int size = this.mRecords.size();
        int size2 = View.MeasureSpec.getSize(i);
        getPaddingStart();
        getPaddingEnd();
        if (View.MeasureSpec.getMode(i2) == 0 && (i3 = this.mColumns) != 0) {
            this.mRows = ((size + i3) - 1) / i3;
        }
        SecTileLayout secTileLayout = this.mSecTileLayout;
        int i4 = secTileLayout.height;
        int i5 = this.mMaxCellHeight;
        Context context = (Context) secTileLayout.contextSupplier.get();
        if (i4 < i5) {
            tileExpandedHeight = context.getResources().getDimensionPixelSize(R.dimen.sec_qs_tile_height_no_label);
        } else {
            secTileLayout.getResourcePicker();
            tileExpandedHeight = SecQSPanelResourcePicker.getTileExpandedHeight(context);
        }
        this.mCellHeight = tileExpandedHeight;
        int asInt = secTileLayout.cellHeightSupplier.getAsInt();
        int i6 = i4 / asInt;
        int asInt2 = secTileLayout.rowsSupplier.getAsInt();
        int i7 = (i4 - (asInt * asInt2)) / (asInt2 + 1);
        if (i7 < 0) {
            i7 = 0;
        }
        secTileLayout.tileVerticalMargin = i7;
        secTileLayout.getResourcePicker();
        int panelWidth = (int) (SecQSPanelResourcePicker.getPanelWidth(context) * 0.0192f);
        int asInt3 = secTileLayout.columnsSupplier.getAsInt();
        int asInt4 = ((size2 - (panelWidth * 2)) - (secTileLayout.cellWidthSupplier.getAsInt() * asInt3)) / (asInt3 + 1);
        this.mCellMarginHorizontal = asInt4;
        this.mSidePadding = asInt4 + panelWidth;
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mMaxCellHeight, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
        Iterator it = this.mRecords.iterator();
        View view = this;
        while (it.hasNext()) {
            SecQSPanelControllerBase.TileRecord tileRecord = (SecQSPanelControllerBase.TileRecord) it.next();
            if (tileRecord.tileView.getVisibility() != 8) {
                this.mSecTileLayout.getClass();
                int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(this.mCellWidth, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
                QSTileView qSTileView = tileRecord.tileView;
                qSTileView.measure(makeMeasureSpec2, makeMeasureSpec);
                view = qSTileView.updateAccessibilityOrder(view);
            }
        }
        super.onMeasure(i, i2);
    }

    @Override // android.view.ViewGroup
    public final void removeAllViews() {
        Iterator it = this.mRecords.iterator();
        while (it.hasNext()) {
            ((SecQSPanelControllerBase.TileRecord) it.next()).tile.setListening(this, false);
        }
        this.mRecords.clear();
        super.removeAllViews();
    }

    @Override // com.android.systemui.qs.SecQSPanel.QSTileLayout
    public final void removeTile(SecQSPanelControllerBase.TileRecord tileRecord) {
        this.mRecords.remove(tileRecord);
        tileRecord.tile.setListening(this, false);
        removeView(tileRecord.tileView);
    }

    public void setListening(boolean z, UiEventLogger uiEventLogger) {
        if (this.mListening == z) {
            return;
        }
        this.mListening = z;
        Iterator it = this.mRecords.iterator();
        while (it.hasNext()) {
            ((SecQSPanelControllerBase.TileRecord) it.next()).tile.setListening(this, this.mListening);
        }
    }

    public final boolean setMaxColumns() {
        int i = this.mColumns;
        int qsTileColumn = ((SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class)).getQsTileColumn(((ViewGroup) this).mContext);
        this.mColumns = qsTileColumn;
        if (i != qsTileColumn) {
            return true;
        }
        return false;
    }

    public boolean updateResources() {
        boolean z;
        Resources resources = getResources();
        this.mCellMarginHorizontal = resources.getDimensionPixelSize(R.dimen.qs_tile_margin_horizontal);
        this.mCellMarginVertical = resources.getDimensionPixelSize(R.dimen.qs_tile_margin_vertical);
        SecTileLayout secTileLayout = this.mSecTileLayout;
        TileLayout$$ExternalSyntheticLambda0 tileLayout$$ExternalSyntheticLambda0 = new TileLayout$$ExternalSyntheticLambda0(this, 4);
        TileLayout$$ExternalSyntheticLambda0 tileLayout$$ExternalSyntheticLambda02 = new TileLayout$$ExternalSyntheticLambda0(this, 5);
        TileLayout$$ExternalSyntheticLambda0 tileLayout$$ExternalSyntheticLambda03 = new TileLayout$$ExternalSyntheticLambda0(this, 6);
        secTileLayout.getClass();
        int i = this.mCellMarginVertical;
        int asInt = secTileLayout.cellMarginHorizontalSupplier.getAsInt();
        if (i == 0) {
            this.mCellMarginVertical = asInt;
        }
        secTileLayout.update(tileLayout$$ExternalSyntheticLambda0, SecTileLayout$updateResources$1.INSTANCE);
        secTileLayout.update(tileLayout$$ExternalSyntheticLambda03, SecTileLayout$updateResources$2.INSTANCE);
        secTileLayout.update(tileLayout$$ExternalSyntheticLambda02, SecTileLayout$updateResources$3.INSTANCE);
        if (((SettingsHelper) secTileLayout.settingsHelper$delegate.getValue()).isQSButtonGridPopupEnabled()) {
            int asInt2 = secTileLayout.lastCellWidthSupplier.getAsInt();
            IntSupplier intSupplier = secTileLayout.cellWidthSupplier;
            if (asInt2 != intSupplier.getAsInt()) {
                this.mCellWidth = intSupplier.getAsInt();
                return true;
            }
        }
        int intValue = ((Number) SecTileLayout$updateResources$4.INSTANCE.invoke(secTileLayout.getResourcePicker(), secTileLayout.contextSupplier.get())).intValue();
        if (secTileLayout.columnsSupplier.getAsInt() != intValue) {
            secTileLayout.columnsConsumer.accept(intValue);
            requestLayout();
            z = true;
        } else {
            z = false;
        }
        if (z) {
            return true;
        }
        return false;
    }

    public TileLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRows = 1;
        ArrayList arrayList = new ArrayList();
        this.mRecords = arrayList;
        final int i = 3;
        this.mMaxAllowedRows = 3;
        this.mMinRows = 1;
        if (Settings.System.getInt(context.getContentResolver(), "qs_less_rows", 0) == 0) {
            Utils.useQsMediaPlayer(context);
        }
        new TextView(context);
        IntSupplier intSupplier = new IntSupplier(this) { // from class: com.android.systemui.qs.TileLayout$$ExternalSyntheticLambda1
            public final /* synthetic */ TileLayout f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.IntSupplier
            public final int getAsInt() {
                switch (i) {
                    case 3:
                        return this.f$0.mCellHeight;
                    case 4:
                        return this.f$0.mLastCellWidth;
                    case 5:
                        return this.f$0.mCellMarginHorizontal;
                    case 6:
                        return this.f$0.mCellWidth;
                    case 7:
                        return this.f$0.mColumns;
                    case 8:
                        return this.f$0.getLayoutDirection();
                    case 9:
                        return this.f$0.mRows;
                    default:
                        return this.f$0.mSidePadding;
                }
            }
        };
        final int i2 = 5;
        IntSupplier intSupplier2 = new IntSupplier(this) { // from class: com.android.systemui.qs.TileLayout$$ExternalSyntheticLambda1
            public final /* synthetic */ TileLayout f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.IntSupplier
            public final int getAsInt() {
                switch (i2) {
                    case 3:
                        return this.f$0.mCellHeight;
                    case 4:
                        return this.f$0.mLastCellWidth;
                    case 5:
                        return this.f$0.mCellMarginHorizontal;
                    case 6:
                        return this.f$0.mCellWidth;
                    case 7:
                        return this.f$0.mColumns;
                    case 8:
                        return this.f$0.getLayoutDirection();
                    case 9:
                        return this.f$0.mRows;
                    default:
                        return this.f$0.mSidePadding;
                }
            }
        };
        final int i3 = 6;
        IntSupplier intSupplier3 = new IntSupplier(this) { // from class: com.android.systemui.qs.TileLayout$$ExternalSyntheticLambda1
            public final /* synthetic */ TileLayout f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.IntSupplier
            public final int getAsInt() {
                switch (i3) {
                    case 3:
                        return this.f$0.mCellHeight;
                    case 4:
                        return this.f$0.mLastCellWidth;
                    case 5:
                        return this.f$0.mCellMarginHorizontal;
                    case 6:
                        return this.f$0.mCellWidth;
                    case 7:
                        return this.f$0.mColumns;
                    case 8:
                        return this.f$0.getLayoutDirection();
                    case 9:
                        return this.f$0.mRows;
                    default:
                        return this.f$0.mSidePadding;
                }
            }
        };
        final int i4 = 8;
        TileLayout$$ExternalSyntheticLambda0 tileLayout$$ExternalSyntheticLambda0 = new TileLayout$$ExternalSyntheticLambda0(this, 8);
        final int i5 = 7;
        IntSupplier intSupplier4 = new IntSupplier(this) { // from class: com.android.systemui.qs.TileLayout$$ExternalSyntheticLambda1
            public final /* synthetic */ TileLayout f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.IntSupplier
            public final int getAsInt() {
                switch (i5) {
                    case 3:
                        return this.f$0.mCellHeight;
                    case 4:
                        return this.f$0.mLastCellWidth;
                    case 5:
                        return this.f$0.mCellMarginHorizontal;
                    case 6:
                        return this.f$0.mCellWidth;
                    case 7:
                        return this.f$0.mColumns;
                    case 8:
                        return this.f$0.getLayoutDirection();
                    case 9:
                        return this.f$0.mRows;
                    default:
                        return this.f$0.mSidePadding;
                }
            }
        };
        Supplier supplier = new Supplier() { // from class: com.android.systemui.qs.TileLayout$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                Context context2;
                context2 = ((ViewGroup) TileLayout.this).mContext;
                return context2;
            }
        };
        IntSupplier intSupplier5 = new IntSupplier(this) { // from class: com.android.systemui.qs.TileLayout$$ExternalSyntheticLambda1
            public final /* synthetic */ TileLayout f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.IntSupplier
            public final int getAsInt() {
                switch (i4) {
                    case 3:
                        return this.f$0.mCellHeight;
                    case 4:
                        return this.f$0.mLastCellWidth;
                    case 5:
                        return this.f$0.mCellMarginHorizontal;
                    case 6:
                        return this.f$0.mCellWidth;
                    case 7:
                        return this.f$0.mColumns;
                    case 8:
                        return this.f$0.getLayoutDirection();
                    case 9:
                        return this.f$0.mRows;
                    default:
                        return this.f$0.mSidePadding;
                }
            }
        };
        IntFunction intFunction = new IntFunction() { // from class: com.android.systemui.qs.TileLayout$$ExternalSyntheticLambda4
            @Override // java.util.function.IntFunction
            public final Object apply(int i6) {
                TileLayout tileLayout = TileLayout.this;
                int i7 = tileLayout.mCellHeight;
                int i8 = tileLayout.mSecTileLayout.tileVerticalMargin;
                return Integer.valueOf(((i7 + i8) * i6) + i8);
            }
        };
        final int i6 = 9;
        IntSupplier intSupplier6 = new IntSupplier(this) { // from class: com.android.systemui.qs.TileLayout$$ExternalSyntheticLambda1
            public final /* synthetic */ TileLayout f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.IntSupplier
            public final int getAsInt() {
                switch (i6) {
                    case 3:
                        return this.f$0.mCellHeight;
                    case 4:
                        return this.f$0.mLastCellWidth;
                    case 5:
                        return this.f$0.mCellMarginHorizontal;
                    case 6:
                        return this.f$0.mCellWidth;
                    case 7:
                        return this.f$0.mColumns;
                    case 8:
                        return this.f$0.getLayoutDirection();
                    case 9:
                        return this.f$0.mRows;
                    default:
                        return this.f$0.mSidePadding;
                }
            }
        };
        final int i7 = 10;
        final int i8 = 4;
        this.mSecTileLayout = new SecTileLayout(intSupplier, intSupplier2, intSupplier3, tileLayout$$ExternalSyntheticLambda0, intSupplier4, supplier, intSupplier5, intFunction, arrayList, intSupplier6, new IntSupplier(this) { // from class: com.android.systemui.qs.TileLayout$$ExternalSyntheticLambda1
            public final /* synthetic */ TileLayout f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.IntSupplier
            public final int getAsInt() {
                switch (i7) {
                    case 3:
                        return this.f$0.mCellHeight;
                    case 4:
                        return this.f$0.mLastCellWidth;
                    case 5:
                        return this.f$0.mCellMarginHorizontal;
                    case 6:
                        return this.f$0.mCellWidth;
                    case 7:
                        return this.f$0.mColumns;
                    case 8:
                        return this.f$0.getLayoutDirection();
                    case 9:
                        return this.f$0.mRows;
                    default:
                        return this.f$0.mSidePadding;
                }
            }
        }, new BiFunction() { // from class: com.android.systemui.qs.TileLayout$$ExternalSyntheticLambda2
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                int i9;
                TileLayout tileLayout = TileLayout.this;
                ((Integer) obj).intValue();
                int intValue = ((Integer) obj2).intValue();
                int i10 = tileLayout.mRows;
                int i11 = tileLayout.mColumns;
                boolean z = true;
                int i12 = (intValue / i11) + 1;
                tileLayout.mRows = i12;
                int i13 = tileLayout.mMinRows;
                if (i12 < i13) {
                    tileLayout.mRows = i13;
                } else {
                    int i14 = tileLayout.mMaxAllowedRows;
                    if (i12 >= i14) {
                        tileLayout.mRows = i14;
                    }
                }
                if (i11 != 0 && tileLayout.mRows > (i9 = ((intValue + i11) - 1) / i11)) {
                    tileLayout.mRows = i9;
                }
                if (intValue == 0) {
                    tileLayout.mRows = 0;
                }
                if (i10 == tileLayout.mRows) {
                    z = false;
                }
                return Boolean.valueOf(z);
            }
        }, new IntSupplier(this) { // from class: com.android.systemui.qs.TileLayout$$ExternalSyntheticLambda1
            public final /* synthetic */ TileLayout f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.IntSupplier
            public final int getAsInt() {
                switch (i8) {
                    case 3:
                        return this.f$0.mCellHeight;
                    case 4:
                        return this.f$0.mLastCellWidth;
                    case 5:
                        return this.f$0.mCellMarginHorizontal;
                    case 6:
                        return this.f$0.mCellWidth;
                    case 7:
                        return this.f$0.mColumns;
                    case 8:
                        return this.f$0.getLayoutDirection();
                    case 9:
                        return this.f$0.mRows;
                    default:
                        return this.f$0.mSidePadding;
                }
            }
        });
        updateResources();
        setClipChildren(false);
        setClipToPadding(false);
    }
}
