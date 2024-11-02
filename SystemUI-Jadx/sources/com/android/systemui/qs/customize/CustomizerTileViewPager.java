package com.android.systemui.qs.customize;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.qs.PagedTileLayout;
import com.android.systemui.qs.SecPageIndicator;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.customize.CustomizerTileViewPager;
import com.android.systemui.qs.customize.SecQSCustomizerBase;
import com.android.systemui.qs.customize.setting.SecQSSettingEditResources;
import com.android.systemui.util.SettingsHelper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public class CustomizerTileViewPager extends ViewPager {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AnonymousClass1 mAdapter;
    public SecQSCustomizerAnimator mAnimator;
    public View.OnClickListener mClickListener;
    public int mColumns;
    public final Context mContext;
    public CustomActionManager mCustomActionManager;
    public final AnonymousClass2 mDistribute;
    public View.OnDragListener mDragListener;
    public SecQSCustomizerBase.CustomTileInfo mDummyTile;
    public int mInitialPagenum;
    public boolean mIsAvailableArea;
    public boolean mIsMultiTouch;
    public boolean mIsTopEdit;
    public SecQSCustomizerBase.CustomTileInfo mLongClickedViewInfo;
    public SecPageIndicator mPageIndicator;
    public float mPageIndicatorPosition;
    public final ArrayList mPages;
    public PagedTileLayout mPanelTileLayout;
    public int mRows;
    public boolean mShowLabel;
    public final ArrayList mTiles;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* renamed from: com.android.systemui.qs.customize.CustomizerTileViewPager$1, reason: invalid class name */
    /* loaded from: classes2.dex */
    public final class AnonymousClass1 extends PagerAdapter {
        public AnonymousClass1() {
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            ListPopupWindow$$ExternalSyntheticOutline0.m("Destantiating ", i, "CSTMPagedTileLayout");
            viewGroup.removeView((View) obj);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final int getCount() {
            return CustomizerTileViewPager.this.mPages.size();
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final int getItemPosition(Object obj) {
            CustomizerTileViewPager customizerTileViewPager = CustomizerTileViewPager.this;
            int indexOf = customizerTileViewPager.mPages.indexOf(obj);
            if (indexOf == -1) {
                return -2;
            }
            if (customizerTileViewPager.isLayoutRtl()) {
                return (customizerTileViewPager.mPages.size() - 1) - indexOf;
            }
            return indexOf;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final Object instantiateItem(ViewGroup viewGroup, int i) {
            ListPopupWindow$$ExternalSyntheticOutline0.m("Instantiating ", i, "CSTMPagedTileLayout");
            CustomizerTileViewPager customizerTileViewPager = CustomizerTileViewPager.this;
            if (customizerTileViewPager.isLayoutRtl()) {
                i = (customizerTileViewPager.mPages.size() - 1) - i;
            }
            ViewGroup viewGroup2 = (ViewGroup) customizerTileViewPager.mPages.get(i);
            viewGroup.addView(viewGroup2);
            return viewGroup2;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final boolean isViewFromObject(View view, Object obj) {
            if (view == obj) {
                return true;
            }
            return false;
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public static class CustomizerTilePage extends CustomizerTileLayout {
        public int mCurrentRows;

        public CustomizerTilePage(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public final boolean isFull() {
            if (this.mCustomTilesInfo.size() >= this.mColumns * this.mMaxRows) {
                return true;
            }
            return false;
        }

        public final boolean updateResources() {
            boolean z;
            int i;
            Log.d("CSTMPagedTileLayout", "updateResources 2");
            if (QpRune.QUICK_TABLET) {
                if (this.mMaxRows != this.mCurrentRows) {
                    z = true;
                } else {
                    z = false;
                }
                if (z) {
                    requestLayout();
                }
            } else {
                z = false;
            }
            this.mContext.getResources();
            this.mCellWidth = withDefaultDensity(R.dimen.qs_edit_tile_expanded_width);
            if (!this.mIsTopEdit && ((SettingsHelper) Dependency.get(SettingsHelper.class)).isQSButtonGridPopupEnabled()) {
                int tileExpandedWidth = ((SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class)).getTileExpandedWidth(this.mContext);
                int i2 = this.mCellWidth;
                if (i2 <= tileExpandedWidth) {
                    tileExpandedWidth = i2;
                }
                this.mCellWidth = tileExpandedWidth;
            }
            int withDefaultDensity = withDefaultDensity(R.dimen.qs_edit_tile_icon_frame_size);
            if (this.mShowLabel) {
                i = withDefaultDensity(R.dimen.qs_edit_tile_label_height);
            } else {
                i = 0;
            }
            this.mCellHeight = withDefaultDensity + i;
            if (z) {
                return true;
            }
            return false;
        }
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.qs.customize.CustomizerTileViewPager$2] */
    public CustomizerTileViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        int i = 0;
        this.mIsMultiTouch = false;
        this.mIsAvailableArea = false;
        this.mShowLabel = true;
        this.mIsTopEdit = false;
        this.mTiles = new ArrayList();
        this.mPages = new ArrayList();
        this.mInitialPagenum = 0;
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.mAdapter = anonymousClass1;
        this.mColumns = 4;
        this.mDummyTile = null;
        this.mDistribute = new Runnable() { // from class: com.android.systemui.qs.customize.CustomizerTileViewPager.2
            @Override // java.lang.Runnable
            public final void run() {
                CustomizerTileViewPager customizerTileViewPager = CustomizerTileViewPager.this;
                int i2 = CustomizerTileViewPager.$r8$clinit;
                customizerTileViewPager.distributeTiles();
            }
        };
        this.mContext = context;
        SecQSPanelResourcePicker secQSPanelResourcePicker = (SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class);
        updateResources();
        setAdapter(anonymousClass1);
        addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.android.systemui.qs.customize.CustomizerTileViewPager.3
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public final void onPageScrolled(float f, int i2, int i3) {
                CustomizerTileViewPager customizerTileViewPager = CustomizerTileViewPager.this;
                SecPageIndicator secPageIndicator = customizerTileViewPager.mPageIndicator;
                if (secPageIndicator == null) {
                    return;
                }
                float f2 = i2 + f;
                secPageIndicator.setLocation(f2);
                customizerTileViewPager.mPageIndicatorPosition = f2;
                customizerTileViewPager.getClass();
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public final void onPageSelected(int i2) {
                int i3;
                CustomizerTileViewPager customizerTileViewPager = CustomizerTileViewPager.this;
                if (customizerTileViewPager.mPageIndicator != null && customizerTileViewPager.mPanelTileLayout != null) {
                    if (customizerTileViewPager.isLayoutRtl()) {
                        i2 = (customizerTileViewPager.mPages.size() - 1) - i2;
                    }
                    if (customizerTileViewPager.mIsMultiTouch && customizerTileViewPager.mLongClickedViewInfo != null) {
                        SecQSCustomizerBase.MessageObjectAnim messageObjectAnim = new SecQSCustomizerBase.MessageObjectAnim();
                        int i4 = customizerTileViewPager.mPanelTileLayout.mCurItem;
                        if (i4 > i2) {
                            i3 = 203;
                        } else {
                            i3 = 204;
                        }
                        messageObjectAnim.animationType = i3;
                        messageObjectAnim.longClickedTileInfo = customizerTileViewPager.mLongClickedViewInfo;
                        customizerTileViewPager.movePage(messageObjectAnim, i4);
                        customizerTileViewPager.mIsMultiTouch = false;
                    }
                    customizerTileViewPager.mPanelTileLayout.setCurrentItem(i2, false);
                }
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public final void onPageScrollStateChanged(int i2) {
            }
        });
        setCurrentItem(0, false);
        boolean z = QpRune.QUICK_TABLET;
        setHorizontalFadingEdgeEnabled(z);
        if (z) {
            secQSPanelResourcePicker.getClass();
            i = (int) (SecQSPanelResourcePicker.getPanelWidth(context) * 0.0192f);
        }
        setFadingEdgeLength(i);
    }

    public final void addPage() {
        int i = 0;
        CustomizerTilePage customizerTilePage = (CustomizerTilePage) LayoutInflater.from(this.mContext).inflate(R.layout.qs_customizer_paged_page, (ViewGroup) this, false);
        customizerTilePage.mIsTopEdit = this.mIsTopEdit;
        customizerTilePage.mClickListener = this.mClickListener;
        int i2 = this.mRows;
        int i3 = this.mColumns;
        if (QpRune.QUICK_TABLET) {
            customizerTilePage.mCurrentRows = customizerTilePage.mMaxRows;
        }
        customizerTilePage.mMaxRows = i2;
        customizerTilePage.mColumns = i3;
        customizerTilePage.mShowLabel = this.mShowLabel;
        customizerTilePage.addBackgroundBox(i2, i3, this.mDragListener);
        customizerTilePage.updateResources();
        this.mPages.add(customizerTilePage);
        int currentItem = getCurrentItem();
        SecPageIndicator secPageIndicator = this.mPageIndicator;
        if (secPageIndicator != null) {
            AnonymousClass1 anonymousClass1 = this.mAdapter;
            if (anonymousClass1 != null) {
                i = anonymousClass1.getCount();
            }
            secPageIndicator.setNumPages(i);
            this.mPageIndicator.setLocation(currentItem);
        }
        this.mAdapter.notifyDataSetChanged();
    }

    public final void addTile(SecQSCustomizerBase.CustomTileInfo customTileInfo, int i) {
        boolean z;
        Log.d("CSTMPagedTileLayout", "addTile: " + customTileInfo);
        int currentItem = getCurrentItem();
        int size = this.mPages.size();
        int i2 = SecQSSettingEditResources.LARGE_POS;
        if (i >= i2) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            currentItem = this.mPages.size() - 1;
            if (currentItem < 0) {
                currentItem = 0;
            }
            if (i < i2 * 2 && getCurrentItem() != currentItem) {
                setCurrentItem(currentItem, false);
            }
        }
        int i3 = size - 1;
        while (true) {
            if (i3 < currentItem) {
                break;
            }
            if (((CustomizerTilePage) this.mPages.get(i3)).isFull()) {
                if (i3 == this.mAdapter.getCount() - 1) {
                    addPage();
                    if (z && i < SecQSSettingEditResources.LARGE_POS * 2) {
                        currentItem++;
                        setCurrentItem(currentItem, false);
                        break;
                    } else {
                        SecQSCustomizerBase.CustomTileInfo customTileInfo2 = (SecQSCustomizerBase.CustomTileInfo) ((CustomizerTilePage) this.mPages.get(i3)).mCustomTilesInfo.get(r2.size() - 1);
                        ((CustomizerTilePage) this.mPages.get(i3 + 1)).addTile(customTileInfo2, 0, false);
                        ((CustomizerTilePage) this.mPages.get(i3)).removeTile(customTileInfo2, false);
                    }
                } else {
                    SecQSCustomizerBase.CustomTileInfo customTileInfo3 = (SecQSCustomizerBase.CustomTileInfo) ((CustomizerTilePage) this.mPages.get(i3)).mCustomTilesInfo.get(r2.size() - 1);
                    ((CustomizerTilePage) this.mPages.get(i3 + 1)).addTile(customTileInfo3, 0, false);
                    ((CustomizerTilePage) this.mPages.get(i3)).removeTile(customTileInfo3, false);
                }
            }
            i3--;
        }
        ((CustomizerTilePage) this.mPages.get(currentItem)).addTile(customTileInfo, i, true);
        ((CustomizerTilePage) this.mPages.get(currentItem)).selectTile(customTileInfo, true);
    }

    public final void addTiles(ArrayList arrayList) {
        this.mTiles.clear();
        for (int i = 0; i < arrayList.size(); i++) {
            this.mTiles.add((SecQSCustomizerBase.CustomTileInfo) arrayList.get(i));
        }
        Log.d("CSTMPagedTileLayout", this.mTiles.size() + " tiles added");
        removeCallbacks(this.mDistribute);
        post(this.mDistribute);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (this.mIsMultiTouch && this.mIsAvailableArea) {
            return true;
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public final void distributeTiles() {
        CustomizerTilePage customizerTilePage;
        int size = this.mPages.size();
        int size2 = this.mTiles.size();
        ArrayList arrayList = new ArrayList();
        removeAllViews();
        if (size == 0) {
            this.mPages.add(makePage());
        }
        int i = 0;
        for (int i2 = 0; i2 < size2; i2++) {
            SecQSCustomizerBase.CustomTileInfo customTileInfo = (SecQSCustomizerBase.CustomTileInfo) this.mTiles.get(i2);
            if (((CustomizerTilePage) this.mPages.get(i)).isFull() && (i = i + 1) == this.mPages.size()) {
                this.mPages.add(makePage());
            }
            ((CustomizerTilePage) this.mPages.get(i)).addTile(customTileInfo);
            arrayList.add(customTileInfo);
        }
        int i3 = i + 1;
        if (this.mPages.size() != i3) {
            while (i3 < this.mPages.size()) {
                Log.d("CSTMPagedTileLayout", "mPages.remove");
                this.mPages.remove(r1.size() - 1);
            }
            SecPageIndicator secPageIndicator = this.mPageIndicator;
            if (secPageIndicator != null) {
                secPageIndicator.setNumPages(this.mPages.size());
            }
            setAdapter(this.mAdapter);
            this.mAdapter.notifyDataSetChanged();
            setCurrentItem(0, false);
        }
        for (int i4 = 0; i4 < this.mPages.size(); i4++) {
            ((CustomizerTilePage) this.mPages.get(i4)).addBackgroundBox(this.mRows, this.mColumns, this.mDragListener);
        }
        SecPageIndicator secPageIndicator2 = this.mPageIndicator;
        if (secPageIndicator2 != null) {
            secPageIndicator2.setNumPages(this.mPages.size());
        }
        setAdapter(this.mAdapter);
        this.mAdapter.notifyDataSetChanged();
        setCurrentItem(this.mInitialPagenum, false);
        SecQSCustomizerAnimator secQSCustomizerAnimator = this.mAnimator;
        if (secQSCustomizerAnimator != null && (customizerTilePage = (CustomizerTilePage) this.mPages.get(0)) != null) {
            customizerTilePage.getChildAt(0);
            int childCount = customizerTilePage.getChildCount();
            for (int i5 = 0; i5 < childCount; i5++) {
                View findViewById = customizerTilePage.getChildAt(i5).findViewById(secQSCustomizerAnimator.mRemoveIconId);
                if (findViewById != null) {
                    findViewById.setScaleX(0.5f);
                    findViewById.setScaleY(0.5f);
                    findViewById.setAlpha(0.0f);
                    findViewById.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(200L).setStartDelay(200L).start();
                }
            }
        }
    }

    public final int getColumnCount() {
        if (this.mPages.size() == 0) {
            return 0;
        }
        return ((CustomizerTilePage) this.mPages.get(0)).mColumns;
    }

    @Override // androidx.viewpager.widget.ViewPager
    public final int getCurrentItem() {
        int i = this.mCurItem;
        if (this.mAdapter != null && isLayoutRtl()) {
            return (this.mAdapter.getCount() - 1) - i;
        }
        return i;
    }

    public final int getMinimumTileNum() {
        int i;
        if (this.mAdapter.getCount() - 1 < this.mPages.size()) {
            i = ((CustomizerTilePage) this.mPages.get(this.mAdapter.getCount() - 1)).mCustomTilesInfo.size();
        } else {
            i = 0;
        }
        return ((this.mPages.size() - 1) * this.mColumns * this.mRows) + i;
    }

    @Override // android.view.View
    public final float getRightFadingEdgeStrength() {
        return 1.0f;
    }

    public final List getSpec() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.mPages.size(); i++) {
            Iterator it = ((CustomizerTilePage) this.mPages.get(i)).mCustomTilesInfo.iterator();
            while (it.hasNext()) {
                arrayList.add(((SecQSCustomizerBase.CustomTileInfo) it.next()).spec);
            }
        }
        Log.d("CSTMPagedTileLayout", "newspecs =  " + arrayList);
        return arrayList;
    }

    public final int getTiledPageIndex(SecQSCustomizerBase.CustomTileInfo customTileInfo) {
        Iterator it = this.mPages.iterator();
        int i = -1;
        while (it.hasNext()) {
            i++;
            if (((CustomizerTileLayout) it.next()).indexOf(customTileInfo) >= 0) {
                return i;
            }
        }
        return -1;
    }

    public final ArrayList getTilesInfo() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.mPages.size(); i++) {
            arrayList.addAll(((CustomizerTilePage) this.mPages.get(i)).mCustomTilesInfo);
        }
        if (this.mPages.size() > 0 && arrayList.size() >= 0) {
            this.mTiles.clear();
            this.mTiles.addAll(arrayList);
        }
        return this.mTiles;
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    public final CustomizerTilePage makePage() {
        CustomizerTilePage customizerTilePage = (CustomizerTilePage) LayoutInflater.from(this.mContext).inflate(R.layout.qs_customizer_paged_page, (ViewGroup) this, false);
        customizerTilePage.mIsTopEdit = this.mIsTopEdit;
        customizerTilePage.mClickListener = this.mClickListener;
        int i = this.mRows;
        int i2 = this.mColumns;
        if (QpRune.QUICK_TABLET) {
            customizerTilePage.mCurrentRows = customizerTilePage.mMaxRows;
        }
        customizerTilePage.mMaxRows = i;
        customizerTilePage.mColumns = i2;
        customizerTilePage.mShowLabel = this.mShowLabel;
        customizerTilePage.mCustomActionManager = this.mCustomActionManager;
        customizerTilePage.updateResources();
        return customizerTilePage;
    }

    public final void movePage(SecQSCustomizerBase.MessageObjectAnim messageObjectAnim, final int i) {
        final int i2;
        int columnCount;
        final int i3;
        int i4;
        int i5 = messageObjectAnim.animationType;
        if (i5 == 204) {
            AnonymousClass1 anonymousClass1 = this.mAdapter;
            if (anonymousClass1 != null) {
                i4 = anonymousClass1.getCount();
            } else {
                i4 = 0;
            }
            if (i >= i4 - 1) {
                return;
            }
        } else if (i <= 0) {
            return;
        }
        if (i5 == 204) {
            i2 = 1;
        } else {
            i2 = -1;
        }
        if (i5 == 204) {
            columnCount = 0;
        } else {
            columnCount = (getColumnCount() * this.mRows) - 1;
        }
        if (i5 == 204) {
            i3 = (getColumnCount() * this.mRows) - 1;
        } else {
            i3 = 0;
        }
        int columnCount2 = (getColumnCount() * this.mRows) - 1;
        final int i6 = i + i2;
        final SecQSCustomizerBase.CustomTileInfo info = ((CustomizerTilePage) this.mPages.get(i6)).getInfo(columnCount);
        if (info == null) {
            return;
        }
        ((CustomizerTilePage) this.mPages.get(i6)).removeTile(info, false);
        final SecQSCustomizerBase.CustomTileInfo customTileInfo = messageObjectAnim.longClickedTileInfo;
        ((CustomizerTilePage) this.mPages.get(i6)).addTile(customTileInfo, columnCount2, false);
        ((CustomizerTilePage) this.mPages.get(i6)).selectTile(customTileInfo, true);
        postDelayed(new Runnable() { // from class: com.android.systemui.qs.customize.CustomizerTileViewPager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                CustomizerTileViewPager customizerTileViewPager = CustomizerTileViewPager.this;
                int i7 = i;
                int i8 = i2;
                int i9 = i6;
                SecQSCustomizerBase.CustomTileInfo customTileInfo2 = customTileInfo;
                SecQSCustomizerBase.CustomTileInfo customTileInfo3 = info;
                int i10 = i3;
                int i11 = CustomizerTileViewPager.$r8$clinit;
                customizerTileViewPager.getClass();
                Log.d("CSTMPagedTileLayout", "cur " + i7 + "pageOffset" + i8);
                if (i7 < customizerTileViewPager.mPages.size() && customizerTileViewPager.mPages.get(i7) != null) {
                    customizerTileViewPager.setCurrentItem(i9, true);
                    ((CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager.mPages.get(i7)).removeTile(customTileInfo2, false);
                    ((CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager.mPages.get(i7)).addTile(customTileInfo3, i10, false);
                    FrameLayout frameLayout = ((CustomizerTileViewPager.CustomizerTilePage) customizerTileViewPager.mPages.get(i7)).mCircle;
                    if (frameLayout != null) {
                        frameLayout.setAlpha(0.0f);
                    }
                }
            }
        }, 210L);
    }

    public final void moveTile(SecQSCustomizerBase.CustomTileInfo customTileInfo, int i) {
        int tiledPageIndex = getTiledPageIndex(customTileInfo);
        Log.d("CSTMPagedTileLayout", "moveTile: " + customTileInfo + ", pageIndex=" + tiledPageIndex);
        if (tiledPageIndex == -1) {
            return;
        }
        int i2 = this.mColumns * this.mRows;
        int i3 = i / i2;
        int i4 = i % i2;
        if (tiledPageIndex > i3) {
            int i5 = i3;
            while (i5 < tiledPageIndex) {
                SecQSCustomizerBase.CustomTileInfo customTileInfo2 = (SecQSCustomizerBase.CustomTileInfo) ((CustomizerTilePage) this.mPages.get(i5)).mCustomTilesInfo.get(r5.size() - 1);
                int i6 = i5 + 1;
                ((CustomizerTilePage) this.mPages.get(i6)).addTile(customTileInfo2, 0, false);
                ((CustomizerTilePage) this.mPages.get(i5)).removeTile(customTileInfo2, false);
                i5 = i6;
            }
        } else {
            int i7 = tiledPageIndex;
            while (i7 < i3) {
                int i8 = i7 + 1;
                SecQSCustomizerBase.CustomTileInfo info = ((CustomizerTilePage) this.mPages.get(i8)).getInfo(0);
                if (info == null) {
                    return;
                }
                ((CustomizerTilePage) this.mPages.get(i7)).addTile(info);
                ((CustomizerTilePage) this.mPages.get(i8)).removeTile(info, false);
                i7 = i8;
            }
        }
        ((CustomizerTilePage) this.mPages.get(tiledPageIndex)).removeTile(customTileInfo, false);
        ((CustomizerTilePage) this.mPages.get(i3)).addTile(customTileInfo, i4, false);
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        boolean z;
        super.onAttachedToWindow();
        int i = this.mContext.getResources().getConfiguration().orientation;
        if (getId() == R.id.qs_customizer_available_pager) {
            z = true;
        } else {
            z = false;
        }
        this.mIsAvailableArea = z;
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.View
    public final void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        setAdapter(this.mAdapter);
        setCurrentItem(0, false);
    }

    @Override // android.view.ViewGroup
    public final void removeAllViews() {
        int size = this.mPages.size();
        for (int i = 0; i < size; i++) {
            ((CustomizerTilePage) this.mPages.get(i)).removeAllViews();
        }
    }

    public final void removePage() {
        int i;
        int currentItem = getCurrentItem();
        this.mPages.remove(r1.size() - 1);
        SecPageIndicator secPageIndicator = this.mPageIndicator;
        if (secPageIndicator != null) {
            AnonymousClass1 anonymousClass1 = this.mAdapter;
            if (anonymousClass1 != null) {
                i = anonymousClass1.getCount();
            } else {
                i = 0;
            }
            secPageIndicator.setNumPages(i);
            if (currentItem == this.mPages.size()) {
                this.mPageIndicator.setLocation(this.mPages.size() - 1);
            } else {
                this.mPageIndicator.setLocation(currentItem);
            }
        }
        this.mAdapter.notifyDataSetChanged();
    }

    public final void removeTile(SecQSCustomizerBase.CustomTileInfo customTileInfo) {
        int tiledPageIndex = getTiledPageIndex(customTileInfo);
        Log.d("CSTMPagedTileLayout", "removeTile: " + customTileInfo + ", pageIndex=" + tiledPageIndex);
        if (tiledPageIndex == -1) {
            return;
        }
        int size = this.mPages.size();
        int i = tiledPageIndex;
        while (i < size - 1) {
            int i2 = i + 1;
            SecQSCustomizerBase.CustomTileInfo info = ((CustomizerTilePage) this.mPages.get(i2)).getInfo(0);
            if (info == null) {
                return;
            }
            ((CustomizerTilePage) this.mPages.get(i)).addTile(info);
            ((CustomizerTilePage) this.mPages.get(i2)).removeTile(info, false);
            if (((CustomizerTilePage) this.mPages.get(i2)).mCustomTilesInfo.size() == 0) {
                removePage();
            }
            i = i2;
        }
        ((CustomizerTilePage) this.mPages.get(tiledPageIndex)).removeTile(customTileInfo, true);
        if (((CustomizerTilePage) this.mPages.get(tiledPageIndex)).mCustomTilesInfo.size() == 0 && tiledPageIndex != 0) {
            removePage();
        }
    }

    @Override // androidx.viewpager.widget.ViewPager
    public final void setCurrentItem(int i, boolean z) {
        if (isLayoutRtl()) {
            i = (this.mPages.size() - 1) - i;
        }
        super.setCurrentItem(i, z);
    }

    public final boolean updateResources() {
        Log.d("CSTMPagedTileLayout", "updateResources");
        boolean z = false;
        for (int i = 0; i < this.mPages.size(); i++) {
            CustomizerTilePage customizerTilePage = (CustomizerTilePage) this.mPages.get(i);
            int i2 = this.mRows;
            int i3 = this.mColumns;
            customizerTilePage.getClass();
            if (QpRune.QUICK_TABLET) {
                customizerTilePage.mCurrentRows = customizerTilePage.mMaxRows;
            }
            customizerTilePage.mMaxRows = i2;
            customizerTilePage.mColumns = i3;
            ((CustomizerTilePage) this.mPages.get(i)).mShowLabel = this.mShowLabel;
            z |= ((CustomizerTilePage) this.mPages.get(i)).updateResources();
        }
        if (z) {
            this.mTiles.clear();
            for (int i4 = 0; i4 < this.mPages.size(); i4++) {
                Iterator it = ((CustomizerTilePage) this.mPages.get(i4)).mCustomTilesInfo.iterator();
                while (it.hasNext()) {
                    this.mTiles.add((SecQSCustomizerBase.CustomTileInfo) it.next());
                }
            }
            removeAllViews();
            distributeTiles();
            return true;
        }
        return true;
    }
}
