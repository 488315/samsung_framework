package com.android.systemui.globalactions.presentation.view;

import android.app.Presentation;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.globalactions.presentation.view.CoverViewAnimatorFSM;
import com.android.systemui.globalactions.util.SystemUIConditions;
import com.android.systemui.qp.util.SubscreenUtil;
import com.samsung.android.globalactions.presentation.SamsungGlobalActionsPresenter;
import com.samsung.android.globalactions.presentation.features.FeatureFactory;
import com.samsung.android.globalactions.presentation.strategies.ViewInflateStrategy;
import com.samsung.android.globalactions.presentation.view.ContentView;
import com.samsung.android.globalactions.presentation.view.ExtendableGlobalActionsView;
import com.samsung.android.globalactions.presentation.view.ResourceFactory;
import com.samsung.android.globalactions.presentation.view.ResourceType;
import com.samsung.android.globalactions.presentation.view.ViewAnimationState;
import com.samsung.android.globalactions.presentation.view.ViewStateController;
import com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel;
import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.HandlerUtil;
import com.samsung.android.globalactions.util.LogWrapper;
import com.samsung.android.globalactions.util.SystemConditions;
import com.samsung.android.globalactions.util.ToastController;
import com.samsung.android.view.SemWindowManager;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class FrontLargeCoverContentView implements ContentView, ViewStateController {
    public ContentAdapter mAdapter;
    public CoverViewAnimatorFSM mAnimatorFSM;
    public FrontLargeCoverGlobalActionsBackgroundView mBackgroundView;
    public final ConditionChecker mConditionChecker;
    public ViewGroup mConfirmView;
    public final Context mContext;
    public final Context mCoverWindowContext;
    public final Presentation mDialog;
    public final FeatureFactory mFeatureFactory;
    public AnonymousClass1 mFoldStateListener;
    public boolean mForceDismiss;
    public final AnonymousClass3 mFrontCoverAnimatorCallback = new AnonymousClass3();
    public final HandlerUtil mHandler;
    public final boolean mIsCameraViewCover;
    public final boolean mIsIconOnly;
    public boolean mIsSecureConfirming;
    public boolean mIsWhiteTheme;
    public boolean mLastFoldedState;
    public ContentGridView mListView;
    public final LogWrapper mLogWrapper;
    public final ExtendableGlobalActionsView mParentView;
    public final SamsungGlobalActionsPresenter mPresenter;
    public final ResourceFactory mResourceFactory;
    public RootView mRootView;
    public ActionViewModel mSelectedViewModel;
    public ViewAnimationState mState;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* renamed from: com.android.systemui.globalactions.presentation.view.FrontLargeCoverContentView$3, reason: invalid class name */
    /* loaded from: classes.dex */
    public final class AnonymousClass3 {
        public AnonymousClass3() {
        }

        public final ViewGroup getConfirmIconLabelView(ViewGroup viewGroup) {
            return (ViewGroup) viewGroup.findViewById(FrontLargeCoverContentView.this.mResourceFactory.get(ResourceType.ID_COVER_BTN_BACKGROUND));
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class ContentAdapter extends RecyclerView.Adapter {
        public View mLastAnimatedView;
        public final List mViewModelList = new ArrayList();
        public final List mTempViewModelList = new ArrayList();
        public boolean mIsConfirmView = false;

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes.dex */
        public final class ViewHolder extends RecyclerView.ViewHolder {
            public static final /* synthetic */ int $r8$clinit = 0;
            public final View view;

            public ViewHolder(ContentAdapter contentAdapter, View view, ViewGroup viewGroup) {
                super(view);
                this.view = view;
            }
        }

        public ContentAdapter() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final int getItemCount() {
            return ((ArrayList) this.mViewModelList).size();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            FrontLargeCoverContentView frontLargeCoverContentView = FrontLargeCoverContentView.this;
            Context context = frontLargeCoverContentView.mDialog.getContext();
            ArrayList arrayList = (ArrayList) this.mViewModelList;
            ActionViewModel actionViewModel = (ActionViewModel) arrayList.get(i);
            int i2 = ViewHolder.$r8$clinit;
            FrontLargeCoverContentItemView frontLargeCoverContentItemView = new FrontLargeCoverContentItemView(context, actionViewModel, null, frontLargeCoverContentView.mIsIconOnly, frontLargeCoverContentView.mIsWhiteTheme, frontLargeCoverContentView.mIsCameraViewCover, frontLargeCoverContentView.mResourceFactory);
            boolean z = this.mIsConfirmView;
            View view = ((ViewHolder) viewHolder).view;
            frontLargeCoverContentItemView.setViewAttrs(view, z);
            frontLargeCoverContentItemView.mViewModel.getActionInfo().setViewIndex(i);
            ActionViewModel actionViewModel2 = frontLargeCoverContentView.mSelectedViewModel;
            if (actionViewModel2 != null && actionViewModel2.getActionInfo().getStateLabel().equals("confirm_dismiss") && ((ActionViewModel) arrayList.get(i)).getActionInfo().getName() == frontLargeCoverContentView.mSelectedViewModel.getActionInfo().getName()) {
                this.mLastAnimatedView = view;
                View view2 = frontLargeCoverContentView.mAdapter.mLastAnimatedView;
                if (view2 != null) {
                    view2.setVisibility(8);
                }
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
            FrontLargeCoverContentView frontLargeCoverContentView = FrontLargeCoverContentView.this;
            return new ViewHolder(this, LayoutInflater.from(frontLargeCoverContentView.mContext).inflate(frontLargeCoverContentView.mResourceFactory.get(ResourceType.LAYOUT_FRONT_LARGE_COVER_ITEM), (ViewGroup) recyclerView, false), recyclerView);
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class ContentGridView extends RecyclerView {
        public ContentGridView(FrontLargeCoverContentView frontLargeCoverContentView, Context context) {
            super(context);
            setFocusable(false);
            setVerticalScrollBarEnabled(false);
        }

        @Override // androidx.recyclerview.widget.RecyclerView, android.view.View
        public final boolean onTouchEvent(MotionEvent motionEvent) {
            if (motionEvent.getAction() == 0) {
                return false;
            }
            return super.onTouchEvent(motionEvent);
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class FrontLargeCoverGlobalActionsBackgroundView extends FrameLayout {
        public FrontLargeCoverGlobalActionsBackgroundView(FrontLargeCoverContentView frontLargeCoverContentView, Context context) {
            super(context);
            addView(View.inflate(context, frontLargeCoverContentView.mResourceFactory.get(ResourceType.LAYOUT_BLUR_BACKGROUND), null));
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class HorizontalSpaceItemDecoration extends RecyclerView.ItemDecoration {
        public final int horizontalSpaceHeight;

        public HorizontalSpaceItemDecoration(FrontLargeCoverContentView frontLargeCoverContentView, int i) {
            this.horizontalSpaceHeight = i;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public final void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            recyclerView.getClass();
            if (RecyclerView.getChildAdapterPosition(view) == 0 || RecyclerView.getChildAdapterPosition(view) == 2) {
                rect.right = this.horizontalSpaceHeight;
            }
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class RootView extends FrameLayout {
        public RootView(Context context) {
            super(context);
            addView(View.inflate(context, FrontLargeCoverContentView.this.mResourceFactory.get(ResourceType.LAYOUT_FRONT_LARGE_COVER_VIEW), null));
        }

        @Override // android.view.ViewGroup, android.view.View
        public final void onAttachedToWindow() {
            super.onAttachedToWindow();
            int i = ((FrameLayout) this).mContext.getResources().getConfiguration().orientation;
            FrontLargeCoverContentView.this.mAnimatorFSM.handleAnimationEvent(CoverViewAnimatorFSM.Event.SHOW);
        }

        @Override // android.view.View
        public final boolean onTouchEvent(MotionEvent motionEvent) {
            if (motionEvent.getAction() == 0) {
                FrontLargeCoverContentView.this.mPresenter.onCancelDialog();
                return true;
            }
            return true;
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {
        public final int verticalSpaceHeight;

        public VerticalSpaceItemDecoration(FrontLargeCoverContentView frontLargeCoverContentView, int i) {
            this.verticalSpaceHeight = i;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public final void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            recyclerView.getClass();
            if (RecyclerView.getChildAdapterPosition(view) == 0 || RecyclerView.getChildAdapterPosition(view) == 1) {
                rect.bottom = this.verticalSpaceHeight;
            }
        }
    }

    /* JADX WARN: Type inference failed for: r5v4, types: [com.android.systemui.globalactions.presentation.view.FrontLargeCoverContentView$1] */
    public FrontLargeCoverContentView(Context context, ExtendableGlobalActionsView extendableGlobalActionsView, FeatureFactory featureFactory, ConditionChecker conditionChecker, ResourceFactory resourceFactory, LogWrapper logWrapper, HandlerUtil handlerUtil, ToastController toastController, SamsungGlobalActionsPresenter samsungGlobalActionsPresenter) {
        DisplayManager displayManager = (DisplayManager) context.getSystemService("display");
        Display display = displayManager.getDisplay(1);
        if (display != null) {
            this.mCoverWindowContext = context.createWindowContext(display, VolteConstants.ErrorCode.REG_NOT_SUBSCRIBED_REASON, null);
        } else {
            this.mCoverWindowContext = context;
        }
        Context context2 = this.mCoverWindowContext;
        this.mContext = context2;
        this.mParentView = extendableGlobalActionsView;
        this.mFeatureFactory = featureFactory;
        this.mConditionChecker = conditionChecker;
        this.mResourceFactory = resourceFactory;
        this.mLogWrapper = logWrapper;
        this.mHandler = handlerUtil;
        this.mPresenter = samsungGlobalActionsPresenter;
        this.mState = ViewAnimationState.IDLE;
        this.mLastFoldedState = true;
        this.mIsSecureConfirming = false;
        boolean isEnabled = conditionChecker.isEnabled(SystemUIConditions.IS_CLEAR_CAMERA_VIEW_COVER_CLOSED);
        this.mIsCameraViewCover = isEnabled;
        if (isEnabled) {
            Display[] displays = displayManager.getDisplays("com.samsung.android.hardware.display.category.VIEW_COVER_DISPLAY");
            Display display2 = displays.length > 0 ? displays[0] : null;
            if (display2 != null) {
                this.mDialog = new Presentation(context2, display2);
                return;
            }
            return;
        }
        Display[] displays2 = displayManager.getDisplays("com.samsung.android.hardware.display.category.BUILTIN");
        if (displays2.length > 1) {
            this.mDialog = new Presentation(context2, displays2[1]);
            Point point = new Point();
            displays2[1].getRealSize(point);
            if (point.x < 512) {
                this.mIsIconOnly = true;
            }
        } else {
            logWrapper.e("FrontLargeCoverContentView", "Failed to get front display. The length of array is : " + displays2.length);
        }
        this.mFoldStateListener = new SemWindowManager.FoldStateListener() { // from class: com.android.systemui.globalactions.presentation.view.FrontLargeCoverContentView.1
            public final void onFoldStateChanged(boolean z) {
                FrontLargeCoverContentView frontLargeCoverContentView = FrontLargeCoverContentView.this;
                frontLargeCoverContentView.mLastFoldedState = z;
                if (!z && !frontLargeCoverContentView.mIsSecureConfirming) {
                    frontLargeCoverContentView.mPresenter.onDismiss();
                    FrontLargeCoverContentView.this.mParentView.dismiss();
                    FrontLargeCoverContentView.this.mDialog.dismiss();
                }
            }

            public final void onTableModeChanged(boolean z) {
            }
        };
        SemWindowManager.getInstance().registerFoldStateListener(this.mFoldStateListener, (Handler) null);
    }

    public final void dismiss() {
        CoverViewAnimatorFSM coverViewAnimatorFSM = this.mAnimatorFSM;
        if (coverViewAnimatorFSM != null) {
            coverViewAnimatorFSM.handleAnimationEvent(CoverViewAnimatorFSM.Event.HIDE);
        }
    }

    public final ViewAnimationState getAnimationState() {
        return this.mState;
    }

    public final ViewAnimationState getState() {
        return this.mState;
    }

    public final void hideConfirm() {
        this.mAnimatorFSM.handleAnimationEvent(CoverViewAnimatorFSM.Event.HIDE_CONFIRM);
    }

    public final void hideDialogOnSecureConfirm() {
        if (this.mConditionChecker.isEnabled(SystemConditions.SUPPORT_SECONDARY_DISPLAY_AS_COVER) && this.mConditionChecker.isEnabled(SystemConditions.IS_FOLDED)) {
            return;
        }
        this.mIsSecureConfirming = true;
        this.mAnimatorFSM.handleAnimationEvent(CoverViewAnimatorFSM.Event.SECURE_CONFIRM);
    }

    public final void initAnimations() {
        FrontLargeCoverViewAnimator frontLargeCoverViewAnimator = new FrontLargeCoverViewAnimator(this.mDialog.getContext(), this.mConditionChecker, this.mLogWrapper, this.mHandler, this.mResourceFactory, this);
        AnonymousClass3 anonymousClass3 = this.mFrontCoverAnimatorCallback;
        frontLargeCoverViewAnimator.mCallback = anonymousClass3;
        frontLargeCoverViewAnimator.mRootView = FrontLargeCoverContentView.this.mRootView;
        this.mAnimatorFSM = new CoverViewAnimatorFSM(frontLargeCoverViewAnimator, this.mLogWrapper, this);
    }

    public final void initLayouts() {
        int dimensionPixelSize;
        long j;
        RootView rootView = new RootView(this.mDialog.getContext());
        this.mRootView = rootView;
        ViewGroup viewGroup = (ViewGroup) rootView.findViewById(this.mResourceFactory.get(ResourceType.ID_FRONT_COVER_ITEM));
        viewGroup.setContentDescription(this.mContext.getString(R.string.global_action_power_off_menu));
        this.mListView = new ContentGridView(this, this.mDialog.getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
        layoutParams.gravity = 17;
        this.mListView.setLayoutParams(layoutParams);
        viewGroup.addView(this.mListView);
        if (this.mIsCameraViewCover) {
            dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(this.mResourceFactory.get(ResourceType.DIMEN_MINI_SVIEW_COVER_SIDE_MARGIN));
        } else {
            dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(this.mResourceFactory.get(ResourceType.DIMEN_FRONT_LARGE_COVER_VERTICAL_SPACE));
        }
        int dimensionPixelSize2 = this.mContext.getResources().getDimensionPixelSize(this.mResourceFactory.get(ResourceType.DIMEN_FRONT_LARGE_COVER_HORIZONTAL_SPACE));
        ContentAdapter contentAdapter = new ContentAdapter();
        this.mAdapter = contentAdapter;
        this.mListView.setAdapter(contentAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.mDialog.getContext(), 2);
        gridLayoutManager.mSpanSizeLookup = new GridLayoutManager.SpanSizeLookup() { // from class: com.android.systemui.globalactions.presentation.view.FrontLargeCoverContentView.2
            @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
            public final int getSpanSize(int i) {
                if (i == 2 && FrontLargeCoverContentView.this.mAdapter.getItemCount() == 3) {
                    return 2;
                }
                return 1;
            }
        };
        this.mListView.setLayoutManager(gridLayoutManager);
        this.mListView.addItemDecoration(new HorizontalSpaceItemDecoration(this, dimensionPixelSize2));
        this.mListView.addItemDecoration(new VerticalSpaceItemDecoration(this, dimensionPixelSize));
        this.mConfirmView = (ViewGroup) this.mRootView.findViewById(this.mResourceFactory.get(ResourceType.ID_FRONT_COVER_COFIRM));
        this.mIsWhiteTheme = this.mConditionChecker.isEnabled(SystemConditions.IS_WHITE_THEME);
        this.mDialog.setContentView(this.mRootView);
        if (this.mConditionChecker.isEnabled(SystemConditions.IS_SUPPORT_SF_EFFECT) || this.mConditionChecker.isEnabled(SystemConditions.IS_SUPPORT_CAPTURED_BLUR)) {
            this.mBackgroundView = new FrontLargeCoverGlobalActionsBackgroundView(this, this.mContext);
            Iterator it = this.mFeatureFactory.createViewInflateStrategy().iterator();
            while (it.hasNext()) {
                ((ViewInflateStrategy) it.next()).onInflateView(this.mBackgroundView);
            }
            this.mDialog.addContentView(this.mBackgroundView, new FrameLayout.LayoutParams(-1, -1));
            this.mRootView.bringToFront();
        }
        Window window = this.mDialog.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        if (this.mIsCameraViewCover) {
            j = 6000;
        } else {
            j = 5000;
        }
        attributes.semSetScreenTimeout(j);
        attributes.semSetScreenDimDuration(0L);
        attributes.format = -3;
        attributes.setTitle(this.mContext.getResources().getString(android.R.string.permdesc_readSyncStats));
        attributes.layoutInDisplayCutoutMode = 3;
        window.getDecorView().setSystemUiVisibility(1792);
        window.setNavigationBarContrastEnforced(false);
        window.setNavigationBarColor(0);
        window.setAttributes(attributes);
        setWallpaperAction("samsung.android.wallpaper.pause");
    }

    public final void onDismiss() {
        Presentation presentation = this.mDialog;
        if (presentation != null) {
            presentation.dismiss();
        }
        if (this.mFoldStateListener != null) {
            SemWindowManager.getInstance().unregisterFoldStateListener(this.mFoldStateListener);
        }
        setWallpaperAction("samsung.android.wallpaper.resume");
    }

    public final void setAnimationState(ViewAnimationState viewAnimationState) {
        this.mState = viewAnimationState;
    }

    public final void setState(ViewAnimationState viewAnimationState) {
        this.mState = viewAnimationState;
    }

    public final void setWallpaperAction(String str) {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this.mContext);
        Bundle bundle = new Bundle();
        bundle.putBoolean("isFolded", SemWindowManager.getInstance().isFolded());
        wallpaperManager.semSendWallpaperCommand(17, str, bundle);
    }

    public final void show() {
        if (this.mConditionChecker.isEnabled(SystemConditions.SUPPORT_SECONDARY_DISPLAY_AS_COVER) && this.mConditionChecker.isEnabled(SystemConditions.IS_FOLDED)) {
            ((SubscreenUtil) Dependency.get(SubscreenUtil.class)).closeSubscreenPanel();
        }
        if (this.mDialog != null) {
            this.mLogWrapper.i("FrontLargeCoverContentView", "show FrontLargeCoverContentView");
            this.mDialog.show();
        } else {
            this.mLogWrapper.e("FrontLargeCoverContentView", "Failed to show front display dialog - the dialog is null.");
        }
    }

    public final void showConfirm(ActionViewModel actionViewModel) {
        this.mSelectedViewModel = actionViewModel;
        this.mAnimatorFSM.handleAnimationEvent(CoverViewAnimatorFSM.Event.SHOW_CONFIRM);
    }

    public final void updateItemLists(SamsungGlobalActionsPresenter samsungGlobalActionsPresenter) {
        List<ActionViewModel> validActions = samsungGlobalActionsPresenter.getValidActions();
        ((ArrayList) this.mAdapter.mViewModelList).clear();
        for (ActionViewModel actionViewModel : validActions) {
            if (!actionViewModel.getActionInfo().getName().equals("screen_capture_popup")) {
                ((ArrayList) this.mAdapter.mViewModelList).add(actionViewModel);
            }
        }
        ContentAdapter contentAdapter = this.mAdapter;
        ArrayList arrayList = (ArrayList) contentAdapter.mTempViewModelList;
        arrayList.clear();
        arrayList.addAll(contentAdapter.mViewModelList);
        this.mAdapter.notifyDataSetChanged();
    }

    public final void forceRequestLayout() {
    }

    public final void initDimens() {
    }
}
