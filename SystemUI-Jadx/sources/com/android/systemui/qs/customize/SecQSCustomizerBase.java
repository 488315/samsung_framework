package com.android.systemui.qs.customize;

import android.content.Context;
import android.view.DisplayCutout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.qs.PagedTileLayout;
import com.android.systemui.qs.SecQSPanelController;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.TileLayout;
import com.android.systemui.qs.customize.SecTileQueryHelper;
import com.android.systemui.qs.customize.setting.SecQSSettingEditResources;
import com.android.systemui.util.DeviceState;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public abstract class SecQSCustomizerBase extends LinearLayout {
    public boolean isShown;
    public int mActiveColumns;
    public int mActiveRows;
    public boolean mActiveShowLabel;
    public final CustomizerTileViewPager mActiveTileLayout;
    public float mActiveWeight;
    public int mAvailableColumns;
    public int mAvailableRows;
    public final CustomizerTileViewPager mAvailableTileLayout;
    public float mAvailableWeight;
    public final Context mContext;
    public int mCutOutHeight;
    public SecQSSettingEditResources mEditResources;
    public boolean mIsDragging;
    public boolean mIsMultiTouch;
    public int mMinNum;
    public LinearLayout mSummary;
    public Toast mToast;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class CustomTileInfo extends SecTileQueryHelper.TileInfo {
        public SecCustomizeTileView customTileView;
        public String customizeTileContentDes;
        public boolean isNewCustomTile;
        public View.OnLongClickListener longClickListener;

        public final String toString() {
            StringBuilder sb = new StringBuilder("CustomTileInfo{longClickListener=");
            sb.append(this.longClickListener);
            sb.append(", customTileView=");
            sb.append(this.customTileView);
            sb.append(", customizeTileContentDes='");
            sb.append(this.customizeTileContentDes);
            sb.append(", isNewCustomTile=");
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.isNewCustomTile, "}");
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class MessageObjectAnim {
        public int animationType;
        public CustomTileInfo longClickedTileInfo;
        public int touchedPos;
    }

    public SecQSCustomizerBase(Context context) {
        super(context);
        this.mIsDragging = false;
        this.mIsMultiTouch = false;
        this.mActiveRows = 3;
        this.mActiveColumns = 4;
        this.mAvailableRows = 2;
        this.mAvailableColumns = 4;
        this.mActiveWeight = 3;
        this.mAvailableWeight = 2;
        this.mActiveShowLabel = true;
        DeviceState.getDisplayHeight(context);
        DeviceState.getDisplayWidth(context);
        this.mContext = context;
        LayoutInflater.from(getContext()).inflate(R.layout.sec_qs_customize_panel_content, this);
        this.mAvailableTileLayout = (CustomizerTileViewPager) findViewById(R.id.qs_customizer_available_pager);
        CustomizerTileViewPager customizerTileViewPager = (CustomizerTileViewPager) findViewById(R.id.qs_customizer_active_pager);
        this.mActiveTileLayout = customizerTileViewPager;
        customizerTileViewPager.mPanelTileLayout = (PagedTileLayout) LayoutInflater.from(context).inflate(R.layout.qs_paged_tile_layout, (ViewGroup) this, false);
        bringToFront();
    }

    @Override // android.view.View
    public final boolean isShown() {
        return this.isShown;
    }

    @Override // android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        DisplayCutout displayCutout = windowInsets.getDisplayCutout();
        if (displayCutout != null) {
            int safeInsetTop = displayCutout.getSafeInsetTop() - displayCutout.getSafeInsetBottom();
            if (safeInsetTop < 0) {
                safeInsetTop = this.mContext.getResources().getDimensionPixelSize(17105670);
            }
            if (this.mCutOutHeight != safeInsetTop) {
                this.mCutOutHeight = safeInsetTop;
                updateResources();
            }
        } else if (this.mCutOutHeight != 0) {
            this.mCutOutHeight = 0;
            updateResources();
        }
        setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());
        return super.onApplyWindowInsets(windowInsets);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() > 1) {
            return true;
        }
        if (motionEvent.getAction() == 2) {
            if (this.mIsDragging) {
                this.mIsMultiTouch = true;
            } else {
                this.mIsMultiTouch = false;
            }
            CustomizerTileViewPager customizerTileViewPager = this.mAvailableTileLayout;
            boolean z = this.mIsMultiTouch;
            customizerTileViewPager.mIsMultiTouch = z;
            this.mActiveTileLayout.mIsMultiTouch = z;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        int i3;
        boolean z;
        boolean z2;
        int i4;
        super.onMeasure(i, i2);
        if (this.mActiveRows > 1) {
            SecQSSettingEditResources secQSSettingEditResources = this.mEditResources;
            SecQSPanelResourcePicker secQSPanelResourcePicker = secQSSettingEditResources.mResourcePicker;
            SecQSPanelController secQSPanelController = secQSPanelResourcePicker.mQsPanelController;
            if (secQSPanelController == null) {
                i4 = -1;
            } else {
                PagedTileLayout pagedTileLayout = (PagedTileLayout) secQSPanelController.mTileLayout;
                if (pagedTileLayout.mPages.size() == 0) {
                    i4 = 0;
                } else {
                    i4 = ((TileLayout) pagedTileLayout.mPages.get(0)).mColumns;
                }
            }
            int qsTileColumn = secQSPanelResourcePicker.getQsTileColumn(secQSSettingEditResources.mContext);
            if (i4 < 0) {
                i4 = qsTileColumn;
            }
            this.mActiveColumns = i4;
        }
        if (!this.mEditResources.isCurrentTopEdit) {
            int i5 = this.mAvailableColumns;
            int i6 = this.mActiveColumns;
            if (i5 < i6) {
                this.mAvailableColumns = i6;
            }
        }
        SecQSPanelResourcePicker secQSPanelResourcePicker2 = (SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class);
        int dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.qs_edit_tile_icon_frame_size);
        if (this.mActiveShowLabel) {
            i3 = getResources().getDimensionPixelOffset(R.dimen.qs_edit_tile_label_height);
        } else {
            i3 = 0;
        }
        int dimensionPixelOffset2 = ((getResources().getDimensionPixelOffset(R.dimen.qs_edit_active_remove_button_size) / 3) + dimensionPixelOffset + i3) * this.mActiveRows;
        int measuredHeight = this.mActiveTileLayout.getMeasuredHeight();
        int dimensionPixelOffset3 = getResources().getDimensionPixelOffset(R.dimen.qs_edit_tile_icon_frame_size);
        Context context = this.mContext;
        secQSPanelResourcePicker2.getClass();
        int labelHeight = (SecQSPanelResourcePicker.getLabelHeight(context) + dimensionPixelOffset3) * this.mAvailableRows;
        int measuredHeight2 = this.mAvailableTileLayout.getMeasuredHeight();
        if (dimensionPixelOffset2 > measuredHeight) {
            z = true;
        } else {
            z = false;
        }
        if (labelHeight > measuredHeight2) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z || z2) {
            int i7 = this.mAvailableRows;
            if (i7 >= 2) {
                while (i7 > 1) {
                    if (measuredHeight2 >= (SecQSPanelResourcePicker.getLabelHeight(this.mContext) + getResources().getDimensionPixelOffset(R.dimen.qs_edit_tile_icon_frame_size)) * i7) {
                        break;
                    } else {
                        i7--;
                    }
                }
                this.mAvailableRows = i7;
                this.mAvailableWeight = i7;
            } else if (i7 == 1) {
                if (findViewById(R.id.qs_edit_available_text).getVisibility() != 8) {
                    findViewById(R.id.qs_edit_available_text).setVisibility(8);
                    findViewById(R.id.qs_available_page_parent).setBackgroundColor(0);
                    findViewById(R.id.qs_available_page_parent).setBackgroundResource(R.drawable.qs_edit_panel_available_background);
                } else {
                    findViewById(R.id.qs_customizer_top_summary).setVisibility(8);
                }
            }
        }
        updateResources();
        super.onMeasure(i, i2);
    }

    public final void updateResources() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.qs_active_page_parent);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
        layoutParams.weight = this.mActiveWeight;
        linearLayout.setLayoutParams(layoutParams);
        CustomizerTileViewPager customizerTileViewPager = this.mActiveTileLayout;
        int i = this.mActiveRows;
        int i2 = this.mActiveColumns;
        customizerTileViewPager.mRows = i;
        customizerTileViewPager.mColumns = i2;
        customizerTileViewPager.mShowLabel = this.mActiveShowLabel;
        customizerTileViewPager.updateResources();
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.qs_available_page_parent);
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) linearLayout2.getLayoutParams();
        layoutParams2.weight = this.mAvailableWeight;
        linearLayout2.setLayoutParams(layoutParams2);
        CustomizerTileViewPager customizerTileViewPager2 = this.mAvailableTileLayout;
        int i3 = this.mAvailableRows;
        int i4 = this.mAvailableColumns;
        customizerTileViewPager2.mRows = i3;
        customizerTileViewPager2.mColumns = i4;
        customizerTileViewPager2.updateResources();
    }
}
