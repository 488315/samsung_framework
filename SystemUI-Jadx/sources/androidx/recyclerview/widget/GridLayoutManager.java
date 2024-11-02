package androidx.recyclerview.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import androidx.core.graphics.drawable.IconCompat$$ExternalSyntheticOutline0;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.GapWorker;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.Arrays;
import java.util.WeakHashMap;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public class GridLayoutManager extends LinearLayoutManager {
    public int[] mCachedBorders;
    public final Rect mDecorInsets;
    public boolean mPendingSpanCountChange;
    public final SparseIntArray mPreLayoutSpanIndexCache;
    public final SparseIntArray mPreLayoutSpanSizeCache;
    public View[] mSet;
    public int mSpanCount;
    public SpanSizeLookup mSpanSizeLookup;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class DefaultSpanSizeLookup extends SpanSizeLookup {
        @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
        public final int getSpanIndex(int i, int i2) {
            return i % i2;
        }

        @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
        public final int getSpanSize(int i) {
            return 1;
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public abstract class SpanSizeLookup {
        public final SparseIntArray mSpanIndexCache = new SparseIntArray();
        public final SparseIntArray mSpanGroupIndexCache = new SparseIntArray();
        public boolean mCacheSpanIndices = false;

        public final int getSpanGroupIndex(int i, int i2) {
            int spanSize = getSpanSize(i);
            int i3 = 0;
            int i4 = 0;
            for (int i5 = 0; i5 < i; i5++) {
                int spanSize2 = getSpanSize(i5);
                i3 += spanSize2;
                if (i3 == i2) {
                    i4++;
                    i3 = 0;
                } else if (i3 > i2) {
                    i4++;
                    i3 = spanSize2;
                }
            }
            if (i3 + spanSize > i2) {
                return i4 + 1;
            }
            return i4;
        }

        /* JADX WARN: Code restructure failed: missing block: B:32:0x004e, code lost:
        
            if (r2 > r10) goto L20;
         */
        /* JADX WARN: Removed duplicated region for block: B:29:0x0045  */
        /* JADX WARN: Removed duplicated region for block: B:33:0x0054  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:25:0x004c -> B:21:0x0051). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:26:0x004e -> B:20:0x003f). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:26:0x004e -> B:21:0x0051). Please report as a decompilation issue!!! */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public int getSpanIndex(int r9, int r10) {
            /*
                r8 = this;
                int r0 = r8.getSpanSize(r9)
                r1 = 0
                if (r0 != r10) goto L8
                return r1
            L8:
                boolean r2 = r8.mCacheSpanIndices
                if (r2 == 0) goto L41
                android.util.SparseIntArray r2 = r8.mSpanIndexCache
                int r3 = r2.size()
                r4 = -1
                int r3 = r3 + r4
                r5 = r1
            L15:
                if (r5 > r3) goto L27
                int r6 = r5 + r3
                int r6 = r6 >>> 1
                int r7 = r2.keyAt(r6)
                if (r7 >= r9) goto L24
                int r5 = r6 + 1
                goto L15
            L24:
                int r3 = r6 + (-1)
                goto L15
            L27:
                int r5 = r5 + r4
                if (r5 < 0) goto L34
                int r3 = r2.size()
                if (r5 >= r3) goto L34
                int r4 = r2.keyAt(r5)
            L34:
                if (r4 < 0) goto L41
                int r2 = r2.get(r4)
                int r3 = r8.getSpanSize(r4)
                int r3 = r3 + r2
            L3f:
                r2 = r3
                goto L51
            L41:
                r2 = r1
                r4 = r2
            L43:
                if (r4 >= r9) goto L54
                int r3 = r8.getSpanSize(r4)
                int r2 = r2 + r3
                if (r2 != r10) goto L4e
                r2 = r1
                goto L51
            L4e:
                if (r2 <= r10) goto L51
                goto L3f
            L51:
                int r4 = r4 + 1
                goto L43
            L54:
                int r0 = r0 + r2
                if (r0 > r10) goto L58
                return r2
            L58:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup.getSpanIndex(int, int):int");
        }

        public abstract int getSpanSize(int i);

        public final void invalidateSpanIndexCache() {
            this.mSpanIndexCache.clear();
        }
    }

    public GridLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mPendingSpanCountChange = false;
        this.mSpanCount = -1;
        this.mPreLayoutSpanSizeCache = new SparseIntArray();
        this.mPreLayoutSpanIndexCache = new SparseIntArray();
        this.mSpanSizeLookup = new DefaultSpanSizeLookup();
        this.mDecorInsets = new Rect();
        setSpanCount(RecyclerView.LayoutManager.getProperties(context, attributeSet, i, i2).spanCount);
    }

    public final void calculateItemBorders(int i) {
        int i2;
        int[] iArr = this.mCachedBorders;
        int i3 = this.mSpanCount;
        if (iArr == null || iArr.length != i3 + 1 || iArr[iArr.length - 1] != i) {
            iArr = new int[i3 + 1];
        }
        int i4 = 0;
        iArr[0] = 0;
        int i5 = i / i3;
        int i6 = i % i3;
        int i7 = 0;
        for (int i8 = 1; i8 <= i3; i8++) {
            i4 += i6;
            if (i4 > 0 && i3 - i4 < i6) {
                i2 = i5 + 1;
                i4 -= i3;
            } else {
                i2 = i5;
            }
            i7 += i2;
            iArr[i8] = i7;
        }
        this.mCachedBorders = iArr;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final boolean checkLayoutParams(RecyclerView.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager
    public final void collectPrefetchPositionsForLayoutState(RecyclerView.State state, LinearLayoutManager.LayoutState layoutState, GapWorker.LayoutPrefetchRegistryImpl layoutPrefetchRegistryImpl) {
        boolean z;
        int i = this.mSpanCount;
        for (int i2 = 0; i2 < this.mSpanCount; i2++) {
            int i3 = layoutState.mCurrentPosition;
            if (i3 >= 0 && i3 < state.getItemCount()) {
                z = true;
            } else {
                z = false;
            }
            if (z && i > 0) {
                int i4 = layoutState.mCurrentPosition;
                layoutPrefetchRegistryImpl.addPosition(i4, Math.max(0, layoutState.mScrollingOffset));
                i -= this.mSpanSizeLookup.getSpanSize(i4);
                layoutState.mCurrentPosition += layoutState.mItemDirection;
            } else {
                return;
            }
        }
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int computeHorizontalScrollOffset(RecyclerView.State state) {
        return computeScrollOffset(state);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int computeHorizontalScrollRange(RecyclerView.State state) {
        return computeScrollRange(state);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int computeVerticalScrollOffset(RecyclerView.State state) {
        return computeScrollOffset(state);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int computeVerticalScrollRange(RecyclerView.State state) {
        return computeScrollRange(state);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager
    public final View findReferenceChild(RecyclerView.Recycler recycler, RecyclerView.State state, boolean z, boolean z2) {
        int i;
        int i2;
        int childCount = getChildCount();
        int i3 = 1;
        if (z2) {
            i2 = getChildCount() - 1;
            i = -1;
            i3 = -1;
        } else {
            i = childCount;
            i2 = 0;
        }
        int itemCount = state.getItemCount();
        ensureLayoutState();
        int startAfterPadding = this.mOrientationHelper.getStartAfterPadding();
        int endAfterPadding = this.mOrientationHelper.getEndAfterPadding();
        View view = null;
        View view2 = null;
        while (i2 != i) {
            View childAt = getChildAt(i2);
            int position = RecyclerView.LayoutManager.getPosition(childAt);
            if (position >= 0 && position < itemCount && getSpanIndex(position, recycler, state) == 0) {
                if (((RecyclerView.LayoutParams) childAt.getLayoutParams()).isItemRemoved()) {
                    if (view2 == null) {
                        view2 = childAt;
                    }
                } else {
                    if (this.mOrientationHelper.getDecoratedStart(childAt) < endAfterPadding && this.mOrientationHelper.getDecoratedEnd(childAt) > startAfterPadding) {
                        return childAt;
                    }
                    if (view == null) {
                        view = childAt;
                    }
                }
            }
            i2 += i3;
        }
        if (view == null) {
            return view2;
        }
        return view;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final RecyclerView.LayoutParams generateDefaultLayoutParams() {
        if (this.mOrientation == 0) {
            return new LayoutParams(-2, -1);
        }
        return new LayoutParams(-1, -2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final RecyclerView.LayoutParams generateLayoutParams(Context context, AttributeSet attributeSet) {
        return new LayoutParams(context, attributeSet);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int getColumnCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.mOrientation == 1) {
            if (state.getItemCount() < 1) {
                return this.mSpanCount;
            }
            if (getSpanGroupIndex(state.getItemCount() - 1, recycler, state) + 1 == 1 && state.getItemCount() < this.mSpanCount) {
                return state.getItemCount();
            }
            return this.mSpanCount;
        }
        if (state.getItemCount() < 1) {
            return 0;
        }
        return getSpanGroupIndex(state.getItemCount() - 1, recycler, state) + 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int getRowCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.mOrientation == 0) {
            return this.mSpanCount;
        }
        if (state.getItemCount() < 1) {
            return 0;
        }
        return getSpanGroupIndex(state.getItemCount() - 1, recycler, state) + 1;
    }

    public final int getSpaceForSpanRange(int i, int i2) {
        if (this.mOrientation == 1 && isLayoutRTL()) {
            int[] iArr = this.mCachedBorders;
            int i3 = this.mSpanCount;
            return iArr[i3 - i] - iArr[(i3 - i) - i2];
        }
        int[] iArr2 = this.mCachedBorders;
        return iArr2[i2 + i] - iArr2[i];
    }

    public final int getSpanGroupIndex(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (!state.mInPreLayout) {
            return this.mSpanSizeLookup.getSpanGroupIndex(i, this.mSpanCount);
        }
        int convertPreLayoutPositionToPostLayout = recycler.convertPreLayoutPositionToPostLayout(i);
        if (convertPreLayoutPositionToPostLayout == -1) {
            IconCompat$$ExternalSyntheticOutline0.m("Cannot find span size for pre layout position. ", i, "GridLayoutManager");
            return 0;
        }
        return this.mSpanSizeLookup.getSpanGroupIndex(convertPreLayoutPositionToPostLayout, this.mSpanCount);
    }

    public final int getSpanIndex(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (!state.mInPreLayout) {
            SpanSizeLookup spanSizeLookup = this.mSpanSizeLookup;
            int i2 = this.mSpanCount;
            if (!spanSizeLookup.mCacheSpanIndices) {
                return spanSizeLookup.getSpanIndex(i, i2);
            }
            SparseIntArray sparseIntArray = spanSizeLookup.mSpanIndexCache;
            int i3 = sparseIntArray.get(i, -1);
            if (i3 != -1) {
                return i3;
            }
            int spanIndex = spanSizeLookup.getSpanIndex(i, i2);
            sparseIntArray.put(i, spanIndex);
            return spanIndex;
        }
        int i4 = this.mPreLayoutSpanIndexCache.get(i, -1);
        if (i4 != -1) {
            return i4;
        }
        int convertPreLayoutPositionToPostLayout = recycler.convertPreLayoutPositionToPostLayout(i);
        if (convertPreLayoutPositionToPostLayout == -1) {
            IconCompat$$ExternalSyntheticOutline0.m("Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:", i, "GridLayoutManager");
            return 0;
        }
        SpanSizeLookup spanSizeLookup2 = this.mSpanSizeLookup;
        int i5 = this.mSpanCount;
        if (!spanSizeLookup2.mCacheSpanIndices) {
            return spanSizeLookup2.getSpanIndex(convertPreLayoutPositionToPostLayout, i5);
        }
        SparseIntArray sparseIntArray2 = spanSizeLookup2.mSpanIndexCache;
        int i6 = sparseIntArray2.get(convertPreLayoutPositionToPostLayout, -1);
        if (i6 != -1) {
            return i6;
        }
        int spanIndex2 = spanSizeLookup2.getSpanIndex(convertPreLayoutPositionToPostLayout, i5);
        sparseIntArray2.put(convertPreLayoutPositionToPostLayout, spanIndex2);
        return spanIndex2;
    }

    public final int getSpanSize(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (!state.mInPreLayout) {
            return this.mSpanSizeLookup.getSpanSize(i);
        }
        int i2 = this.mPreLayoutSpanSizeCache.get(i, -1);
        if (i2 != -1) {
            return i2;
        }
        int convertPreLayoutPositionToPostLayout = recycler.convertPreLayoutPositionToPostLayout(i);
        if (convertPreLayoutPositionToPostLayout == -1) {
            IconCompat$$ExternalSyntheticOutline0.m("Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:", i, "GridLayoutManager");
            return 1;
        }
        return this.mSpanSizeLookup.getSpanSize(convertPreLayoutPositionToPostLayout);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager
    public final void layoutChunk(RecyclerView.Recycler recycler, RecyclerView.State state, LinearLayoutManager.LayoutState layoutState, LinearLayoutManager.LayoutChunkResult layoutChunkResult) {
        boolean z;
        int i;
        boolean z2;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int childMeasureSpec;
        int i11;
        boolean z3;
        boolean z4;
        View next;
        int modeInOther = this.mOrientationHelper.getModeInOther();
        int i12 = 1;
        if (modeInOther != 1073741824) {
            z = true;
        } else {
            z = false;
        }
        if (getChildCount() > 0) {
            i = this.mCachedBorders[this.mSpanCount];
        } else {
            i = 0;
        }
        if (z) {
            updateMeasurements();
        }
        if (layoutState.mItemDirection == 1) {
            z2 = true;
        } else {
            z2 = false;
        }
        int i13 = this.mSpanCount;
        if (!z2) {
            i13 = getSpanIndex(layoutState.mCurrentPosition, recycler, state) + getSpanSize(layoutState.mCurrentPosition, recycler, state);
        }
        int i14 = 0;
        while (i14 < this.mSpanCount) {
            int i15 = layoutState.mCurrentPosition;
            if (i15 >= 0 && i15 < state.getItemCount()) {
                z4 = true;
            } else {
                z4 = false;
            }
            if (!z4 || i13 <= 0) {
                break;
            }
            int i16 = layoutState.mCurrentPosition;
            int spanSize = getSpanSize(i16, recycler, state);
            if (spanSize <= this.mSpanCount) {
                i13 -= spanSize;
                if (i13 < 0 || (next = layoutState.next(recycler)) == null) {
                    break;
                }
                this.mSet[i14] = next;
                i14++;
            } else {
                throw new IllegalArgumentException(ConstraintWidget$$ExternalSyntheticOutline0.m(GridLayoutManager$$ExternalSyntheticOutline0.m("Item at position ", i16, " requires ", spanSize, " spans but GridLayoutManager has only "), this.mSpanCount, " spans."));
            }
        }
        if (i14 == 0) {
            layoutChunkResult.mFinished = true;
            return;
        }
        if (z2) {
            i2 = 0;
            i3 = i14;
        } else {
            i2 = i14 - 1;
            i12 = -1;
            i3 = -1;
        }
        int i17 = 0;
        while (i2 != i3) {
            View view = this.mSet[i2];
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            int spanSize2 = getSpanSize(RecyclerView.LayoutManager.getPosition(view), recycler, state);
            layoutParams.mSpanSize = spanSize2;
            layoutParams.mSpanIndex = i17;
            i17 += spanSize2;
            i2 += i12;
        }
        float f = 0.0f;
        int i18 = 0;
        for (int i19 = 0; i19 < i14; i19++) {
            View view2 = this.mSet[i19];
            if (layoutState.mScrapList == null) {
                if (z2) {
                    z3 = false;
                    addViewInt(view2, -1, false);
                } else {
                    z3 = false;
                    addViewInt(view2, 0, false);
                }
            } else {
                z3 = false;
                if (z2) {
                    addViewInt(view2, -1, true);
                } else {
                    addViewInt(view2, 0, true);
                }
            }
            calculateItemDecorationsForChild(this.mDecorInsets, view2);
            measureChild(view2, modeInOther, z3);
            int decoratedMeasurement = this.mOrientationHelper.getDecoratedMeasurement(view2);
            if (decoratedMeasurement > i18) {
                i18 = decoratedMeasurement;
            }
            float decoratedMeasurementInOther = (this.mOrientationHelper.getDecoratedMeasurementInOther(view2) * 1.0f) / ((LayoutParams) view2.getLayoutParams()).mSpanSize;
            if (decoratedMeasurementInOther > f) {
                f = decoratedMeasurementInOther;
            }
        }
        if (z) {
            calculateItemBorders(Math.max(Math.round(f * this.mSpanCount), i));
            i18 = 0;
            for (int i20 = 0; i20 < i14; i20++) {
                View view3 = this.mSet[i20];
                measureChild(view3, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS, true);
                int decoratedMeasurement2 = this.mOrientationHelper.getDecoratedMeasurement(view3);
                if (decoratedMeasurement2 > i18) {
                    i18 = decoratedMeasurement2;
                }
            }
        }
        for (int i21 = 0; i21 < i14; i21++) {
            View view4 = this.mSet[i21];
            if (this.mOrientationHelper.getDecoratedMeasurement(view4) != i18) {
                LayoutParams layoutParams2 = (LayoutParams) view4.getLayoutParams();
                Rect rect = layoutParams2.mDecorInsets;
                int i22 = rect.top + rect.bottom + ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams2).bottomMargin;
                int i23 = rect.left + rect.right + ((ViewGroup.MarginLayoutParams) layoutParams2).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams2).rightMargin;
                int spaceForSpanRange = getSpaceForSpanRange(layoutParams2.mSpanIndex, layoutParams2.mSpanSize);
                if (this.mOrientation == 1) {
                    i11 = RecyclerView.LayoutManager.getChildMeasureSpec(false, spaceForSpanRange, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS, i23, ((ViewGroup.MarginLayoutParams) layoutParams2).width);
                    childMeasureSpec = View.MeasureSpec.makeMeasureSpec(i18 - i22, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
                } else {
                    int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i18 - i23, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
                    childMeasureSpec = RecyclerView.LayoutManager.getChildMeasureSpec(false, spaceForSpanRange, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS, i22, ((ViewGroup.MarginLayoutParams) layoutParams2).height);
                    i11 = makeMeasureSpec;
                }
                if (shouldReMeasureChild(view4, i11, childMeasureSpec, (RecyclerView.LayoutParams) view4.getLayoutParams())) {
                    view4.measure(i11, childMeasureSpec);
                }
            }
        }
        layoutChunkResult.mConsumed = i18;
        if (this.mOrientation == 1) {
            if (layoutState.mLayoutDirection == -1) {
                i10 = layoutState.mOffset;
                i9 = i10 - i18;
            } else {
                i9 = layoutState.mOffset;
                i10 = i18 + i9;
            }
            i7 = 0;
            i6 = i9;
            i8 = i10;
            i5 = 0;
        } else {
            if (layoutState.mLayoutDirection == -1) {
                i5 = layoutState.mOffset;
                i4 = i5 - i18;
            } else {
                i4 = layoutState.mOffset;
                i5 = i18 + i4;
            }
            i6 = 0;
            i7 = i4;
            i8 = 0;
        }
        for (int i24 = 0; i24 < i14; i24++) {
            View view5 = this.mSet[i24];
            LayoutParams layoutParams3 = (LayoutParams) view5.getLayoutParams();
            if (this.mOrientation == 1) {
                if (isLayoutRTL()) {
                    i5 = getPaddingLeft() + this.mCachedBorders[this.mSpanCount - layoutParams3.mSpanIndex];
                    i7 = i5 - this.mOrientationHelper.getDecoratedMeasurementInOther(view5);
                } else {
                    i7 = this.mCachedBorders[layoutParams3.mSpanIndex] + getPaddingLeft();
                    i5 = this.mOrientationHelper.getDecoratedMeasurementInOther(view5) + i7;
                }
            } else {
                int paddingTop = getPaddingTop() + this.mCachedBorders[layoutParams3.mSpanIndex];
                i6 = paddingTop;
                i8 = this.mOrientationHelper.getDecoratedMeasurementInOther(view5) + paddingTop;
            }
            RecyclerView.LayoutManager.layoutDecoratedWithMargins(view5, i7, i6, i5, i8);
            if (layoutParams3.isItemRemoved() || layoutParams3.isItemChanged()) {
                layoutChunkResult.mIgnoreConsumed = true;
            }
            layoutChunkResult.mFocusable = view5.hasFocusable() | layoutChunkResult.mFocusable;
        }
        Arrays.fill(this.mSet, (Object) null);
    }

    public final void measureChild(View view, int i, boolean z) {
        int i2;
        int i3;
        boolean shouldMeasureChild;
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        Rect rect = layoutParams.mDecorInsets;
        int i4 = rect.top + rect.bottom + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
        int i5 = rect.left + rect.right + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
        int spaceForSpanRange = getSpaceForSpanRange(layoutParams.mSpanIndex, layoutParams.mSpanSize);
        if (this.mOrientation == 1) {
            i3 = RecyclerView.LayoutManager.getChildMeasureSpec(false, spaceForSpanRange, i, i5, ((ViewGroup.MarginLayoutParams) layoutParams).width);
            i2 = RecyclerView.LayoutManager.getChildMeasureSpec(true, this.mOrientationHelper.getTotalSpace(), this.mHeightMode, i4, ((ViewGroup.MarginLayoutParams) layoutParams).height);
        } else {
            int childMeasureSpec = RecyclerView.LayoutManager.getChildMeasureSpec(false, spaceForSpanRange, i, i4, ((ViewGroup.MarginLayoutParams) layoutParams).height);
            int childMeasureSpec2 = RecyclerView.LayoutManager.getChildMeasureSpec(true, this.mOrientationHelper.getTotalSpace(), this.mWidthMode, i5, ((ViewGroup.MarginLayoutParams) layoutParams).width);
            i2 = childMeasureSpec;
            i3 = childMeasureSpec2;
        }
        RecyclerView.LayoutParams layoutParams2 = (RecyclerView.LayoutParams) view.getLayoutParams();
        if (z) {
            shouldMeasureChild = shouldReMeasureChild(view, i3, i2, layoutParams2);
        } else {
            shouldMeasureChild = shouldMeasureChild(view, i3, i2, layoutParams2);
        }
        if (shouldMeasureChild) {
            view.measure(i3, i2);
        }
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager
    public final void onAnchorReady(RecyclerView.Recycler recycler, RecyclerView.State state, LinearLayoutManager.AnchorInfo anchorInfo, int i) {
        boolean z;
        updateMeasurements();
        if (state.getItemCount() > 0 && !state.mInPreLayout) {
            if (i == 1) {
                z = true;
            } else {
                z = false;
            }
            int spanIndex = getSpanIndex(anchorInfo.mPosition, recycler, state);
            if (z) {
                while (spanIndex > 0) {
                    int i2 = anchorInfo.mPosition;
                    if (i2 <= 0) {
                        break;
                    }
                    int i3 = i2 - 1;
                    anchorInfo.mPosition = i3;
                    spanIndex = getSpanIndex(i3, recycler, state);
                }
            } else {
                int itemCount = state.getItemCount() - 1;
                int i4 = anchorInfo.mPosition;
                while (i4 < itemCount) {
                    int i5 = i4 + 1;
                    int spanIndex2 = getSpanIndex(i5, recycler, state);
                    if (spanIndex2 <= spanIndex) {
                        break;
                    }
                    i4 = i5;
                    spanIndex = spanIndex2;
                }
                anchorInfo.mPosition = i4;
            }
        }
        View[] viewArr = this.mSet;
        if (viewArr == null || viewArr.length != this.mSpanCount) {
            this.mSet = new View[this.mSpanCount];
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:67:0x00cb, code lost:
    
        if (r13 == r7) goto L58;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x00fb, code lost:
    
        if (r13 == r7) goto L75;
     */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0107  */
    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.view.View onFocusSearchFailed(android.view.View r23, int r24, androidx.recyclerview.widget.RecyclerView.Recycler r25, androidx.recyclerview.widget.RecyclerView.State r26) {
        /*
            Method dump skipped, instructions count: 327
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.GridLayoutManager.onFocusSearchFailed(android.view.View, int, androidx.recyclerview.widget.RecyclerView$Recycler, androidx.recyclerview.widget.RecyclerView$State):android.view.View");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onInitializeAccessibilityNodeInfo(RecyclerView.Recycler recycler, RecyclerView.State state, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        super.onInitializeAccessibilityNodeInfo(recycler, state, accessibilityNodeInfoCompat);
        accessibilityNodeInfoCompat.setClassName(GridView.class.getName());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onInitializeAccessibilityNodeInfoForItem(RecyclerView.Recycler recycler, RecyclerView.State state, View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (!(layoutParams instanceof LayoutParams)) {
            onInitializeAccessibilityNodeInfoForItem(view, accessibilityNodeInfoCompat);
            return;
        }
        LayoutParams layoutParams2 = (LayoutParams) layoutParams;
        int spanGroupIndex = getSpanGroupIndex(layoutParams2.getViewLayoutPosition(), recycler, state);
        if (this.mOrientation == 0) {
            accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(false, layoutParams2.mSpanIndex, layoutParams2.mSpanSize, spanGroupIndex, 1, false));
        } else {
            accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(false, spanGroupIndex, 1, layoutParams2.mSpanIndex, layoutParams2.mSpanSize, false));
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onItemsAdded(int i, int i2) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
        this.mSpanSizeLookup.mSpanGroupIndexCache.clear();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onItemsChanged() {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
        this.mSpanSizeLookup.mSpanGroupIndexCache.clear();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onItemsMoved(int i, int i2) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
        this.mSpanSizeLookup.mSpanGroupIndexCache.clear();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onItemsRemoved(int i, int i2) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
        this.mSpanSizeLookup.mSpanGroupIndexCache.clear();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onItemsUpdated(RecyclerView recyclerView, int i, int i2) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
        this.mSpanSizeLookup.mSpanGroupIndexCache.clear();
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        boolean z = state.mInPreLayout;
        SparseIntArray sparseIntArray = this.mPreLayoutSpanIndexCache;
        SparseIntArray sparseIntArray2 = this.mPreLayoutSpanSizeCache;
        if (z) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                LayoutParams layoutParams = (LayoutParams) getChildAt(i).getLayoutParams();
                int viewLayoutPosition = layoutParams.getViewLayoutPosition();
                sparseIntArray2.put(viewLayoutPosition, layoutParams.mSpanSize);
                sparseIntArray.put(viewLayoutPosition, layoutParams.mSpanIndex);
            }
        }
        super.onLayoutChildren(recycler, state);
        sparseIntArray2.clear();
        sparseIntArray.clear();
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onLayoutCompleted(RecyclerView.State state) {
        super.onLayoutCompleted(state);
        this.mPendingSpanCountChange = false;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int scrollHorizontallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        updateMeasurements();
        View[] viewArr = this.mSet;
        if (viewArr == null || viewArr.length != this.mSpanCount) {
            this.mSet = new View[this.mSpanCount];
        }
        return super.scrollHorizontallyBy(i, recycler, state);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int scrollVerticallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        updateMeasurements();
        View[] viewArr = this.mSet;
        if (viewArr == null || viewArr.length != this.mSpanCount) {
            this.mSet = new View[this.mSpanCount];
        }
        return super.scrollVerticallyBy(i, recycler, state);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void setMeasuredDimension(int i, int i2, Rect rect) {
        int chooseSize;
        int chooseSize2;
        if (this.mCachedBorders == null) {
            super.setMeasuredDimension(i, i2, rect);
        }
        int paddingRight = getPaddingRight() + getPaddingLeft();
        int paddingBottom = getPaddingBottom() + getPaddingTop();
        if (this.mOrientation == 1) {
            int height = rect.height() + paddingBottom;
            RecyclerView recyclerView = this.mRecyclerView;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            chooseSize2 = RecyclerView.LayoutManager.chooseSize(i2, height, ViewCompat.Api16Impl.getMinimumHeight(recyclerView));
            int[] iArr = this.mCachedBorders;
            chooseSize = RecyclerView.LayoutManager.chooseSize(i, iArr[iArr.length - 1] + paddingRight, ViewCompat.Api16Impl.getMinimumWidth(this.mRecyclerView));
        } else {
            int width = rect.width() + paddingRight;
            RecyclerView recyclerView2 = this.mRecyclerView;
            WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
            chooseSize = RecyclerView.LayoutManager.chooseSize(i, width, ViewCompat.Api16Impl.getMinimumWidth(recyclerView2));
            int[] iArr2 = this.mCachedBorders;
            chooseSize2 = RecyclerView.LayoutManager.chooseSize(i2, iArr2[iArr2.length - 1] + paddingBottom, ViewCompat.Api16Impl.getMinimumHeight(this.mRecyclerView));
        }
        this.mRecyclerView.setMeasuredDimension(chooseSize, chooseSize2);
    }

    public final void setSpanCount(int i) {
        if (i == this.mSpanCount) {
            return;
        }
        this.mPendingSpanCountChange = true;
        if (i >= 1) {
            this.mSpanCount = i;
            this.mSpanSizeLookup.invalidateSpanIndexCache();
            requestLayout();
            return;
        }
        throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Span count should be at least 1. Provided ", i));
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager
    public final void setStackFromEnd(boolean z) {
        if (!z) {
            super.setStackFromEnd(false);
            return;
        }
        throw new UnsupportedOperationException("GridLayoutManager does not support stack from end. Consider using reverse layout");
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final boolean supportsPredictiveItemAnimations() {
        if (this.mPendingSavedState == null && !this.mPendingSpanCountChange) {
            return true;
        }
        return false;
    }

    public final void updateMeasurements() {
        int paddingBottom;
        int paddingTop;
        if (this.mOrientation == 1) {
            paddingBottom = this.mWidth - getPaddingRight();
            paddingTop = getPaddingLeft();
        } else {
            paddingBottom = this.mHeight - getPaddingBottom();
            paddingTop = getPaddingTop();
        }
        calculateItemBorders(paddingBottom - paddingTop);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public static class LayoutParams extends RecyclerView.LayoutParams {
        public int mSpanIndex;
        public int mSpanSize;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.mSpanIndex = -1;
            this.mSpanSize = 0;
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.mSpanIndex = -1;
            this.mSpanSize = 0;
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.mSpanIndex = -1;
            this.mSpanSize = 0;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.mSpanIndex = -1;
            this.mSpanSize = 0;
        }

        public LayoutParams(RecyclerView.LayoutParams layoutParams) {
            super(layoutParams);
            this.mSpanIndex = -1;
            this.mSpanSize = 0;
        }
    }

    public GridLayoutManager(Context context, int i) {
        super(context);
        this.mPendingSpanCountChange = false;
        this.mSpanCount = -1;
        this.mPreLayoutSpanSizeCache = new SparseIntArray();
        this.mPreLayoutSpanIndexCache = new SparseIntArray();
        this.mSpanSizeLookup = new DefaultSpanSizeLookup();
        this.mDecorInsets = new Rect();
        setSpanCount(i);
    }

    public GridLayoutManager(Context context, int i, int i2, boolean z) {
        super(context, i2, z);
        this.mPendingSpanCountChange = false;
        this.mSpanCount = -1;
        this.mPreLayoutSpanSizeCache = new SparseIntArray();
        this.mPreLayoutSpanIndexCache = new SparseIntArray();
        this.mSpanSizeLookup = new DefaultSpanSizeLookup();
        this.mDecorInsets = new Rect();
        setSpanCount(i);
    }
}
