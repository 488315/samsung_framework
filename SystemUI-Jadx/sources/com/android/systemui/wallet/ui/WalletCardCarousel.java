package com.android.systemui.wallet.ui;

import android.content.Context;
import android.graphics.Rect;
import android.service.quickaccesswallet.WalletCard;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ImageView;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate;
import com.android.systemui.R;
import com.android.systemui.wallet.ui.WalletCardCarousel;
import com.android.systemui.wallet.ui.WalletScreenController;
import java.util.Collections;
import java.util.List;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public class WalletCardCarousel extends RecyclerView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public float mCardCenterToScreenCenterDistancePx;
    public float mCardEdgeToCenterDistance;
    public int mCardHeightPx;
    public int mCardMarginPx;
    public OnCardScrollListener mCardScrollListener;
    public int mCardWidthPx;
    public int mCenteredAdapterPosition;
    public float mCornerRadiusPx;
    public float mEdgeToCenterDistance;
    public int mExpectedViewWidth;
    public OnSelectionListener mSelectionListener;
    public final Rect mSystemGestureExclusionZone;
    public int mTotalCardWidth;
    public final WalletCardCarouselAdapter mWalletCardCarouselAdapter;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class CardCarouselAccessibilityDelegate extends RecyclerViewAccessibilityDelegate {
        public /* synthetic */ CardCarouselAccessibilityDelegate(WalletCardCarousel walletCardCarousel, RecyclerView recyclerView, int i) {
            this(recyclerView);
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public final boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            if (accessibilityEvent.getEventType() == 32768) {
                WalletCardCarousel walletCardCarousel = WalletCardCarousel.this;
                walletCardCarousel.getClass();
                walletCardCarousel.scrollToPosition(RecyclerView.getChildAdapterPosition(view));
            }
            return super.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
        }

        private CardCarouselAccessibilityDelegate(RecyclerView recyclerView) {
            super(recyclerView);
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class CardCarouselScrollListener extends RecyclerView.OnScrollListener {
        public int mOldState;

        public /* synthetic */ CardCarouselScrollListener(WalletCardCarousel walletCardCarousel, int i) {
            this();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public final void onScrollStateChanged(RecyclerView recyclerView, int i) {
            if (i == 0 && i != this.mOldState) {
                WalletCardCarousel.this.performHapticFeedback(1);
            }
            this.mOldState = i;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public final void onScrolled(RecyclerView recyclerView, int i, int i2) {
            WalletCardCarousel walletCardCarousel = WalletCardCarousel.this;
            int i3 = -1;
            walletCardCarousel.mCenteredAdapterPosition = -1;
            walletCardCarousel.mEdgeToCenterDistance = Float.MAX_VALUE;
            walletCardCarousel.mCardCenterToScreenCenterDistancePx = Float.MAX_VALUE;
            for (int i4 = 0; i4 < walletCardCarousel.getChildCount(); i4++) {
                walletCardCarousel.updateCardView(walletCardCarousel.getChildAt(i4));
            }
            int i5 = walletCardCarousel.mCenteredAdapterPosition;
            if (i5 != -1 && i != 0) {
                if (walletCardCarousel.mEdgeToCenterDistance > 0.0f) {
                    i3 = 1;
                }
                int i6 = i5 + i3;
                if (i6 >= 0 && i6 < walletCardCarousel.mWalletCardCarouselAdapter.mData.size()) {
                    ((WalletView) walletCardCarousel.mCardScrollListener).onCardScroll((WalletCardViewInfo) walletCardCarousel.mWalletCardCarouselAdapter.mData.get(walletCardCarousel.mCenteredAdapterPosition), (WalletCardViewInfo) walletCardCarousel.mWalletCardCarouselAdapter.mData.get(i6), Math.abs(walletCardCarousel.mEdgeToCenterDistance) / walletCardCarousel.mCardEdgeToCenterDistance);
                }
            }
        }

        private CardCarouselScrollListener() {
            this.mOldState = -1;
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class CarouselSnapHelper extends PagerSnapHelper {
        public /* synthetic */ CarouselSnapHelper(WalletCardCarousel walletCardCarousel, int i) {
            this();
        }

        @Override // androidx.recyclerview.widget.PagerSnapHelper, androidx.recyclerview.widget.SnapHelper
        public final RecyclerView.SmoothScroller createScroller(final RecyclerView.LayoutManager layoutManager) {
            return new LinearSmoothScroller(WalletCardCarousel.this.getContext()) { // from class: com.android.systemui.wallet.ui.WalletCardCarousel.CarouselSnapHelper.1
                @Override // androidx.recyclerview.widget.LinearSmoothScroller
                public final float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                    return 200.0f / displayMetrics.densityDpi;
                }

                @Override // androidx.recyclerview.widget.LinearSmoothScroller
                public final int calculateTimeForScrolling(int i) {
                    return Math.min(80, super.calculateTimeForScrolling(i));
                }

                @Override // androidx.recyclerview.widget.LinearSmoothScroller, androidx.recyclerview.widget.RecyclerView.SmoothScroller
                public final void onTargetFound(View view, RecyclerView.SmoothScroller.Action action) {
                    int[] calculateDistanceToFinalSnap = CarouselSnapHelper.this.calculateDistanceToFinalSnap(layoutManager, view);
                    int i = calculateDistanceToFinalSnap[0];
                    int i2 = calculateDistanceToFinalSnap[1];
                    int calculateTimeForDeceleration = calculateTimeForDeceleration(Math.max(Math.abs(i), Math.abs(i2)));
                    if (calculateTimeForDeceleration > 0) {
                        action.update(i, i2, calculateTimeForDeceleration, this.mDecelerateInterpolator);
                    }
                }
            };
        }

        @Override // androidx.recyclerview.widget.PagerSnapHelper, androidx.recyclerview.widget.SnapHelper
        public final View findSnapView(RecyclerView.LayoutManager layoutManager) {
            View findSnapView = super.findSnapView(layoutManager);
            if (findSnapView == null) {
                return null;
            }
            WalletCardViewInfo walletCardViewInfo = ((WalletCardViewHolder) findSnapView.getTag()).mCardViewInfo;
            WalletCardCarousel walletCardCarousel = WalletCardCarousel.this;
            ((WalletScreenController) walletCardCarousel.mSelectionListener).onCardSelected(walletCardViewInfo);
            ((WalletView) walletCardCarousel.mCardScrollListener).onCardScroll(walletCardViewInfo, walletCardViewInfo, 0.0f);
            return findSnapView;
        }

        private CarouselSnapHelper() {
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public interface OnCardScrollListener {
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public interface OnSelectionListener {
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class WalletCardCarouselAdapter extends RecyclerView.Adapter {
        public List mData;

        public /* synthetic */ WalletCardCarouselAdapter(WalletCardCarousel walletCardCarousel, int i) {
            this();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final int getItemCount() {
            return this.mData.size();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final long getItemId(int i) {
            return ((WalletScreenController.QAWalletCardViewInfo) ((WalletCardViewInfo) this.mData.get(i))).getCardId().hashCode();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
            WalletCardViewHolder walletCardViewHolder = (WalletCardViewHolder) viewHolder;
            WalletCardViewInfo walletCardViewInfo = (WalletCardViewInfo) this.mData.get(i);
            walletCardViewHolder.mCardViewInfo = walletCardViewInfo;
            final WalletScreenController.QAWalletCardViewInfo qAWalletCardViewInfo = (WalletScreenController.QAWalletCardViewInfo) walletCardViewInfo;
            boolean isEmpty = qAWalletCardViewInfo.getCardId().isEmpty();
            ImageView imageView = walletCardViewHolder.mImageView;
            if (isEmpty) {
                imageView.setScaleType(ImageView.ScaleType.CENTER);
            }
            imageView.setImageDrawable(qAWalletCardViewInfo.mCardDrawable);
            CharSequence contentDescription = qAWalletCardViewInfo.mWalletCard.getContentDescription();
            CardView cardView = walletCardViewHolder.mCardView;
            cardView.setContentDescription(contentDescription);
            cardView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.wallet.ui.WalletCardCarousel$WalletCardCarouselAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    WalletCard walletCard;
                    WalletCardCarousel.WalletCardCarouselAdapter walletCardCarouselAdapter = WalletCardCarousel.WalletCardCarouselAdapter.this;
                    int i2 = i;
                    WalletCardViewInfo walletCardViewInfo2 = qAWalletCardViewInfo;
                    WalletCardCarousel walletCardCarousel = WalletCardCarousel.this;
                    if (i2 != walletCardCarousel.mCenteredAdapterPosition) {
                        WalletScreenController walletScreenController = (WalletScreenController) walletCardCarousel.mSelectionListener;
                        if (!walletScreenController.mFalsingManager.isFalseTap(1)) {
                            walletScreenController.mCardCarousel.smoothScrollToPosition(i2);
                            return;
                        }
                        return;
                    }
                    WalletScreenController walletScreenController2 = (WalletScreenController) walletCardCarousel.mSelectionListener;
                    if (!walletScreenController2.mFalsingManager.isFalseTap(1) && (walletCardViewInfo2 instanceof WalletScreenController.QAWalletCardViewInfo) && (walletCard = ((WalletScreenController.QAWalletCardViewInfo) walletCardViewInfo2).mWalletCard) != null && walletCard.getPendingIntent() != null) {
                        if (!walletScreenController2.mKeyguardStateController.isUnlocked()) {
                            walletScreenController2.mUiEventLogger.log(WalletUiEvent.QAW_UNLOCK_FROM_CARD_CLICK);
                        }
                        walletScreenController2.mUiEventLogger.log(WalletUiEvent.QAW_CLICK_CARD);
                        walletScreenController2.mActivityStarter.startPendingIntentDismissingKeyguard(((WalletScreenController.QAWalletCardViewInfo) walletCardViewInfo2).mWalletCard.getPendingIntent());
                    }
                }
            });
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
            View inflate = LayoutInflater.from(recyclerView.getContext()).inflate(R.layout.wallet_card_view, (ViewGroup) recyclerView, false);
            WalletCardViewHolder walletCardViewHolder = new WalletCardViewHolder(inflate);
            WalletCardCarousel walletCardCarousel = WalletCardCarousel.this;
            float f = walletCardCarousel.mCornerRadiusPx;
            CardView cardView = walletCardViewHolder.mCardView;
            cardView.setRadius(f);
            ViewGroup.LayoutParams layoutParams = cardView.getLayoutParams();
            layoutParams.width = walletCardCarousel.mCardWidthPx;
            layoutParams.height = walletCardCarousel.mCardHeightPx;
            inflate.setTag(walletCardViewHolder);
            return walletCardViewHolder;
        }

        private WalletCardCarouselAdapter() {
            this.mData = Collections.EMPTY_LIST;
        }
    }

    public WalletCardCarousel(Context context) {
        this(context, null);
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        int width = getWidth();
        if (this.mWalletCardCarouselAdapter.getItemCount() > 1 && width < this.mTotalCardWidth * 1.5d) {
            this.mSystemGestureExclusionZone.set(0, 0, width, getHeight());
            setSystemGestureExclusionRects(Collections.singletonList(this.mSystemGestureExclusionZone));
        }
        if (width != this.mExpectedViewWidth) {
            updatePadding(width);
        }
    }

    @Override // android.view.ViewGroup
    public final void onViewAdded(final View view) {
        super.onViewAdded(view);
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        int i = this.mCardMarginPx;
        ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin = i;
        ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin = i;
        view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.wallet.ui.WalletCardCarousel$$ExternalSyntheticLambda0
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view2, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
                WalletCardCarousel walletCardCarousel = WalletCardCarousel.this;
                View view3 = view;
                int i10 = WalletCardCarousel.$r8$clinit;
                walletCardCarousel.updateCardView(view3);
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public final void scrollToPosition(int i) {
        super.scrollToPosition(i);
        ((WalletScreenController) this.mSelectionListener).onCardSelected((WalletCardViewInfo) this.mWalletCardCarouselAdapter.mData.get(i));
    }

    public final void updateCardView(View view) {
        int left;
        CardView cardView = ((WalletCardViewHolder) view.getTag()).mCardView;
        float width = getWidth() / 2.0f;
        float left2 = (view.getLeft() + view.getRight()) / 2.0f;
        float f = left2 - width;
        float max = Math.max(0.83f, 1.0f - Math.abs(f / view.getWidth()));
        cardView.setScaleX(max);
        cardView.setScaleY(max);
        if (left2 < width) {
            left = view.getRight() + this.mCardMarginPx;
        } else {
            left = view.getLeft() - this.mCardMarginPx;
        }
        if (Math.abs(f) >= this.mCardCenterToScreenCenterDistancePx || RecyclerView.getChildAdapterPosition(view) == -1) {
            return;
        }
        this.mCenteredAdapterPosition = RecyclerView.getChildAdapterPosition(view);
        this.mEdgeToCenterDistance = left - width;
        this.mCardCenterToScreenCenterDistancePx = Math.abs(f);
    }

    public final void updatePadding(int i) {
        int i2;
        RecyclerView.ViewHolder findViewHolderForAdapterPosition;
        int max = Math.max(0, ((i - this.mTotalCardWidth) / 2) - this.mCardMarginPx);
        setPadding(max, getPaddingTop(), max, getPaddingBottom());
        WalletCardCarouselAdapter walletCardCarouselAdapter = this.mWalletCardCarouselAdapter;
        if (walletCardCarouselAdapter != null && walletCardCarouselAdapter.getItemCount() > 0 && (i2 = this.mCenteredAdapterPosition) != -1 && (findViewHolderForAdapterPosition = findViewHolderForAdapterPosition(i2)) != null) {
            View view = findViewHolderForAdapterPosition.itemView;
            scrollBy(((view.getRight() + view.getLeft()) / 2) - ((getRight() + getLeft()) / 2), 0);
        }
    }

    public WalletCardCarousel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSystemGestureExclusionZone = new Rect();
        this.mCenteredAdapterPosition = -1;
        this.mEdgeToCenterDistance = Float.MAX_VALUE;
        this.mCardCenterToScreenCenterDistancePx = Float.MAX_VALUE;
        int i = 0;
        setLayoutManager(new LinearLayoutManager(context, 0, false));
        addOnScrollListener(new CardCarouselScrollListener(this, i));
        new CarouselSnapHelper(this, i).attachToRecyclerView(this);
        WalletCardCarouselAdapter walletCardCarouselAdapter = new WalletCardCarouselAdapter(this, i);
        this.mWalletCardCarouselAdapter = walletCardCarouselAdapter;
        walletCardCarouselAdapter.setHasStableIds(true);
        setAdapter(walletCardCarouselAdapter);
        ViewCompat.setAccessibilityDelegate(this, new CardCarouselAccessibilityDelegate(this, this, i));
        addItemDecoration(new DotIndicatorDecoration(getContext()));
    }
}
