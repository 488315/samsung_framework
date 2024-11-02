package com.android.systemui.qs.bar;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.airbnb.lottie.LottieAnimationView;
import com.android.keyguard.KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logAssistantVisible$2$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.qs.QSBackupRestoreManager;
import com.android.systemui.qs.SecQSPanelController;
import com.android.systemui.qs.SecQSPanelControllerBase;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.tileimpl.SecQSTileView;
import com.android.systemui.settings.brightness.BrightnessController;
import com.android.systemui.settings.brightness.BrightnessDetail;
import com.android.systemui.settings.brightness.BrightnessSliderController;
import com.android.systemui.settings.brightness.BrightnessSliderController$$ExternalSyntheticLambda1;
import com.android.systemui.settings.brightness.BrightnessSliderView;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.SecBrightnessMirrorControllerProvider;
import com.android.systemui.statusbar.policy.BrightnessMirrorController;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.sec.ims.configuration.DATA;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Objects;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class BrightnessBar extends BarItemImpl implements TunerService.Tunable, TileHostable {
    public static final Uri EMERGENCY_MODE_URI = Settings.System.getUriFor("emergency_mode");
    public int mBarBottomMargin;
    public LinearLayout mBrightnessBarContainer;
    public BrightnessController mBrightnessController;
    public final BrightnessController.Factory mBrightnessControllerFactory;
    public ImageView mBrightnessDetailIcon;
    public BrightnessMirrorController mBrightnessMirrorController;
    public final AnonymousClass2 mBrightnessMirrorListener;
    public BrightnessSliderController mBrightnessSliderController;
    public final BrightnessSliderController.Factory mBrightnessSliderControllerFactory;
    public final Context mContext;
    public boolean mIsAllowedOnTop;
    public final QSBackupRestoreManager mQSBackupRestoreManager;
    public final Lazy mQsPanelControllerLazy;
    public final SecQSPanelResourcePicker mResourcePicker;
    public final Lazy mSecBrightnessMirrorControllerProviderLazy;
    public final SettingsHelper mSettingsHelper;
    public final BrightnessBar$$ExternalSyntheticLambda0 mSettingsListener;
    public RelativeLayout mSliderContainer;
    public LinearLayout mTileLayout;
    public final ArrayList mTiles;
    public final TunerService mTunerService;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* renamed from: com.android.systemui.qs.bar.BrightnessBar$2, reason: invalid class name */
    /* loaded from: classes2.dex */
    public final class AnonymousClass2 {
        public AnonymousClass2() {
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.systemui.util.SettingsHelper$OnChangedCallback, com.android.systemui.qs.bar.BrightnessBar$$ExternalSyntheticLambda0] */
    public BrightnessBar(Context context, BrightnessController.Factory factory, BrightnessSliderController.Factory factory2, Lazy lazy, TunerService tunerService, SettingsHelper settingsHelper, QSBackupRestoreManager qSBackupRestoreManager, Lazy lazy2) {
        super(context);
        this.mBarBottomMargin = 0;
        ?? r1 = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.qs.bar.BrightnessBar$$ExternalSyntheticLambda0
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                BrightnessBar.this.updateBrightnessDetail();
            }
        };
        this.mSettingsListener = r1;
        this.mTiles = new ArrayList();
        this.mBrightnessMirrorListener = new AnonymousClass2();
        this.mContext = context;
        this.mBrightnessControllerFactory = factory;
        this.mBrightnessSliderControllerFactory = factory2;
        this.mSecBrightnessMirrorControllerProviderLazy = lazy;
        this.mTunerService = tunerService;
        this.mSettingsHelper = settingsHelper;
        this.mQSBackupRestoreManager = qSBackupRestoreManager;
        this.mQsPanelControllerLazy = lazy2;
        this.mResourcePicker = (SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class);
        tunerService.addTunable(this, "brightness_on_top");
        settingsHelper.registerCallback(r1, EMERGENCY_MODE_URI);
        this.mIsAllowedOnTop = tunerService.getValue(1, "brightness_on_top") != 0;
        qSBackupRestoreManager.addCallback("BrightnessOnTop", new QSBackupRestoreManager.Callback() { // from class: com.android.systemui.qs.bar.BrightnessBar.1
            @Override // com.android.systemui.qs.QSBackupRestoreManager.Callback
            public final boolean isValidDB() {
                boolean z = true;
                int value = BrightnessBar.this.mTunerService.getValue(1, "brightness_on_top");
                if (value != 0 && value != 1) {
                    z = false;
                }
                KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0.m("brightnessOnTop : ", value, " valid : ", z, "BrightnessOnTop");
                return z;
            }

            @Override // com.android.systemui.qs.QSBackupRestoreManager.Callback
            public final String onBackup(boolean z) {
                StringBuilder sb = new StringBuilder("TAG::brightness_on_top::");
                BrightnessBar brightnessBar = BrightnessBar.this;
                String str = DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
                if (z) {
                    String value = brightnessBar.mTunerService.getValue("brightness_on_top");
                    if (value != null) {
                        str = value;
                    }
                    sb.append(str);
                } else {
                    brightnessBar.getClass();
                    sb.append(DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
                }
                Log.d("BrightnessOnTop", "builder : " + ((Object) sb));
                return sb.toString();
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r0v1 */
            /* JADX WARN: Type inference failed for: r0v2, types: [int, boolean] */
            /* JADX WARN: Type inference failed for: r0v3 */
            @Override // com.android.systemui.qs.QSBackupRestoreManager.Callback
            public final void onRestore(String str) {
                BrightnessBar brightnessBar = BrightnessBar.this;
                brightnessBar.getClass();
                String[] split = str.split("::");
                ?? r0 = 0;
                if (split[0].equals("brightness_on_top")) {
                    if (Integer.parseInt(split[1]) == 1) {
                        r0 = 1;
                    }
                    StringBuilder m = RowView$$ExternalSyntheticOutline0.m("isAllowedOnTop : ", r0, "   Integer.parseInt(sp[1]) : ");
                    m.append(Integer.parseInt(split[1]));
                    Log.d("BrightnessOnTop", m.toString());
                    if (brightnessBar.mIsAllowedOnTop != r0) {
                        brightnessBar.mIsAllowedOnTop = r0;
                        brightnessBar.mTunerService.setValue((int) r0, "brightness_on_top");
                    }
                }
            }
        });
    }

    @Override // com.android.systemui.qs.bar.TileHostable
    public final void addTile(SecQSPanelControllerBase.TileRecord tileRecord) {
        ArrayList arrayList = this.mTiles;
        arrayList.add(tileRecord);
        LinearLayout linearLayout = this.mTileLayout;
        if (linearLayout != null) {
            linearLayout.addView(tileRecord.tileView);
            updateTileLayoutSizeMargins();
        }
        BrightnessMirrorController brightnessMirrorController = this.mBrightnessMirrorController;
        if (brightnessMirrorController != null) {
            ArrayList arrayList2 = brightnessMirrorController.tilesOnMirror;
            arrayList2.clear();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                arrayList2.add((SecQSPanelControllerBase.TileRecord) it.next());
            }
        }
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void destroy() {
        this.mCallback = null;
        this.mTunerService.removeTunable(this);
        BrightnessMirrorController brightnessMirrorController = this.mBrightnessMirrorController;
        if (brightnessMirrorController != null) {
            brightnessMirrorController.mBrightnessMirrorListeners.remove(this.mBrightnessMirrorListener);
        }
        BrightnessController brightnessController = this.mBrightnessController;
        if (brightnessController != null) {
            brightnessController.mBackgroundHandler.post(brightnessController.mStopListeningRunnable);
            brightnessController.mControlValueInitialized = false;
        }
        BrightnessSliderController brightnessSliderController = this.mBrightnessSliderController;
        if (brightnessSliderController != null) {
            ((BrightnessSliderView) brightnessSliderController.mView).mToggleDetailListener = null;
        }
        LinkedHashMap linkedHashMap = this.mQSBackupRestoreManager.mQSBnRMap;
        if (linkedHashMap.keySet().contains("BrightnessOnTop")) {
            linkedHashMap.remove("BrightnessOnTop");
        }
        this.mSettingsHelper.unregisterCallback(this.mSettingsListener);
        removeAllTiles();
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final int getBarHeight() {
        if (!this.mShowing) {
            return 0;
        }
        boolean z = this.mIsOnCollapsedState;
        Context context = this.mContext;
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.mResourcePicker;
        if (z) {
            secQSPanelResourcePicker.getClass();
            return SecQSPanelResourcePicker.getBrightnessBarHeight(context) + this.mBarBottomMargin;
        }
        secQSPanelResourcePicker.getClass();
        if (QpRune.QUICK_TABLET) {
            return context.getResources().getDimensionPixelSize(R.dimen.brightness_bar_height_tablet);
        }
        return context.getResources().getDimensionPixelSize(R.dimen.brightness_bar_height);
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final int getBarLayout() {
        return 0;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void inflateViews(ViewGroup viewGroup) {
        BrightnessSliderController.Factory factory = this.mBrightnessSliderControllerFactory;
        Context context = this.mContext;
        BrightnessSliderController create = factory.create(context, viewGroup);
        this.mBrightnessSliderController = create;
        View view = create.mView;
        this.mBarRootView = view;
        this.mBrightnessBarContainer = (LinearLayout) view.findViewById(R.id.brightness_bar_container);
        this.mTileLayout = (LinearLayout) this.mBarRootView.findViewById(R.id.brightness_tile_layout);
        RelativeLayout relativeLayout = (RelativeLayout) this.mBarRootView.findViewById(R.id.slider_container);
        this.mSliderContainer = relativeLayout;
        if (this.mIsOnCollapsedState) {
            relativeLayout.setPadding(0, relativeLayout.getPaddingTop(), 0, this.mSliderContainer.getPaddingBottom());
        }
        this.mBrightnessSliderController.init();
        BrightnessSliderController brightnessSliderController = this.mBrightnessSliderController;
        ((BrightnessSliderView) brightnessSliderController.mView).mToggleDetailListener = this;
        BrightnessController.Factory factory2 = this.mBrightnessControllerFactory;
        factory2.getClass();
        BrightnessController brightnessController = new BrightnessController(factory2.mContext, brightnessSliderController, factory2.mUserTracker, factory2.mDisplayTracker, factory2.mMainExecutor, factory2.mBackgroundHandler);
        this.mBrightnessController = brightnessController;
        brightnessController.mBackgroundHandler.post(brightnessController.mStartListeningRunnable);
        if (this.mListening) {
            this.mBrightnessController.checkRestrictionAndSetEnabled();
        }
        Lazy lazy = this.mSecBrightnessMirrorControllerProviderLazy;
        if (lazy != null) {
            BrightnessMirrorController brightnessMirrorController = ((CentralSurfacesImpl) ((SecBrightnessMirrorControllerProvider) lazy.get())).mBrightnessMirrorController;
            this.mBrightnessMirrorController = brightnessMirrorController;
            AnonymousClass2 anonymousClass2 = this.mBrightnessMirrorListener;
            if (brightnessMirrorController != null) {
                brightnessMirrorController.mBrightnessMirrorListeners.remove(anonymousClass2);
            }
            this.mBrightnessMirrorController = brightnessMirrorController;
            if (brightnessMirrorController != null) {
                Objects.requireNonNull(anonymousClass2);
                brightnessMirrorController.mBrightnessMirrorListeners.add(anonymousClass2);
            }
            updateBrightnessMirror();
        }
        if (!this.mIsOnCollapsedState) {
            this.mTileLayout.setVisibility(0);
            ArrayList arrayList = this.mTiles;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                this.mTileLayout.addView(((SecQSPanelControllerBase.TileRecord) arrayList.get(i)).tileView);
            }
        }
        final BrightnessDetail brightnessDetail = new BrightnessDetail(context, (SecQSPanelController) this.mQsPanelControllerLazy.get(), factory2);
        ImageView imageView = (ImageView) this.mBarRootView.findViewById(R.id.brightness_detail);
        this.mBrightnessDetailIcon = imageView;
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.bar.BrightnessBar$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                BrightnessDetail brightnessDetail2 = BrightnessDetail.this;
                view2.setClickable(true);
                view2.setEnabled(true);
                SecQSPanelController secQSPanelController = brightnessDetail2.mQSPanelController;
                if (secQSPanelController != null) {
                    BrightnessDetail.AnonymousClass1 anonymousClass1 = brightnessDetail2.mBrightnessDetailAdapter;
                    SecQSPanelControllerBase.Record record = new SecQSPanelControllerBase.Record(0);
                    record.mDetailAdapter = anonymousClass1;
                    secQSPanelController.showDetail(record, true);
                }
                SystemUIAnalytics.sendRunstoneEventLog(SystemUIAnalytics.sCurrentScreenID, "QPPE1010", "QUICK_PANEL_LAYOUT");
                SystemUIAnalytics.sendScreenViewLog("QPD101");
            }
        });
        this.mBrightnessDetailIcon.setClickable(true);
        updateBrightnessDetail();
        updateVisibility();
        updateHeightMargins();
        BrightnessSliderView brightnessSliderView = (BrightnessSliderView) this.mBarRootView;
        boolean z = this.mIsOnCollapsedState;
        if (brightnessSliderView.mIsCollapsed != z) {
            brightnessSliderView.mIsCollapsed = z;
            brightnessSliderView.updateSliderResources();
            brightnessSliderView.setDualSeekBarResources(false);
        }
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void onConfigChanged(Configuration configuration) {
        updateBrightnessMirror();
        updateHeightMargins();
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void onKnoxPolicyChanged() {
        updateVisibility();
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public final void onTuningChanged(String str, String str2) {
        String m = FontProvider$$ExternalSyntheticOutline0.m("onTuningChanged() : key = ", str, ", newValue = ", str2);
        String str3 = this.TAG;
        Log.d(str3, m);
        if ("brightness_on_top".equals(str)) {
            boolean z = true;
            if (str2 == null) {
                this.mIsAllowedOnTop = true;
            } else {
                if (Integer.parseInt(str2) == 0) {
                    z = false;
                }
                this.mIsAllowedOnTop = z;
            }
            updateVisibility();
            ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("onTuningChanged() : BRIGHTNESS_ON_TOP = "), this.mIsAllowedOnTop, str3);
        }
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void onUiModeChanged() {
        if (this.mBarRootView == null) {
            return;
        }
        ImageView imageView = this.mBrightnessDetailIcon;
        if (imageView != null) {
            imageView.setBackground(this.mContext.getDrawable(R.drawable.ripple_drawable_20dp));
            this.mBrightnessDetailIcon.getBackground().setState(new int[]{android.R.attr.state_enabled});
        }
        BrightnessController brightnessController = this.mBrightnessController;
        if (brightnessController != null) {
            brightnessController.mHandler.obtainMessage(9).sendToTarget();
        }
    }

    @Override // com.android.systemui.qs.bar.TileHostable
    public final void removeAllTiles() {
        BrightnessMirrorController brightnessMirrorController = this.mBrightnessMirrorController;
        if (brightnessMirrorController != null) {
            brightnessMirrorController.tilesOnMirror.clear();
        }
        LinearLayout linearLayout = this.mTileLayout;
        ArrayList arrayList = this.mTiles;
        if (linearLayout != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                this.mTileLayout.removeView(((SecQSPanelControllerBase.TileRecord) arrayList.get(i)).tileView);
                ((SecQSPanelControllerBase.TileRecord) arrayList.get(i)).tile.setListening(this, false);
            }
        }
        arrayList.clear();
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void setExpanded(boolean z) {
        BrightnessController brightnessController = this.mBrightnessController;
        if (brightnessController != null) {
            brightnessController.checkRestrictionAndSetEnabled();
        }
        BrightnessMirrorController brightnessMirrorController = this.mBrightnessMirrorController;
        if (brightnessMirrorController != null) {
            brightnessMirrorController.mExpanded = z;
        }
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void setListening(boolean z) {
        BrightnessController brightnessController;
        this.mListening = z;
        if (z && (brightnessController = this.mBrightnessController) != null) {
            brightnessController.checkRestrictionAndSetEnabled();
        }
        ArrayList arrayList = this.mTiles;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            ((SecQSPanelControllerBase.TileRecord) arrayList.get(i)).tile.setListening(this, this.mListening);
        }
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void setPosition(float f) {
        BrightnessController brightnessController;
        if (!Float.isNaN(f) && f < 0.1f && (brightnessController = this.mBrightnessController) != null) {
            brightnessController.checkRestrictionAndSetEnabled();
            updateVisibility();
        }
    }

    public final void updateBrightnessDetail() {
        FrameLayout frameLayout;
        View findViewById;
        int i;
        boolean isEmergencyMode = this.mSettingsHelper.isEmergencyMode();
        Log.d(this.TAG, KeyguardUpdateMonitorLogger$logAssistantVisible$2$$ExternalSyntheticOutline0.m("BrightnessDetail disabled = ", isEmergencyMode));
        ImageView imageView = this.mBrightnessDetailIcon;
        int i2 = 8;
        if (imageView != null) {
            if (isEmergencyMode) {
                i = 8;
            } else {
                i = 0;
            }
            imageView.setVisibility(i);
        }
        BrightnessMirrorController brightnessMirrorController = this.mBrightnessMirrorController;
        if (brightnessMirrorController != null && (frameLayout = brightnessMirrorController.mBrightnessMirror) != null && (findViewById = frameLayout.findViewById(R.id.brightness_detail_container)) != null) {
            if (!isEmergencyMode) {
                i2 = 0;
            }
            findViewById.setVisibility(i2);
        }
    }

    public final void updateBrightnessMirror() {
        BrightnessMirrorController brightnessMirrorController = this.mBrightnessMirrorController;
        if (brightnessMirrorController != null) {
            BrightnessSliderController brightnessSliderController = this.mBrightnessSliderController;
            brightnessSliderController.mMirrorController = brightnessMirrorController;
            BrightnessSliderController brightnessSliderController2 = brightnessMirrorController.mToggleSliderController;
            brightnessSliderController.mMirror = brightnessSliderController2;
            if (brightnessSliderController2 != null) {
                brightnessSliderController2.setMax(((BrightnessSliderView) brightnessSliderController.mView).mSlider.getMax());
                brightnessSliderController.mMirror.setValue(((BrightnessSliderView) brightnessSliderController.mView).mSlider.getProgress());
                ((BrightnessSliderView) brightnessSliderController.mView).mListener = new BrightnessSliderController$$ExternalSyntheticLambda1(brightnessSliderController);
            } else {
                ((BrightnessSliderView) brightnessSliderController.mView).mListener = null;
            }
        }
        BrightnessSliderController brightnessSliderController3 = this.mBrightnessSliderController;
        if (brightnessSliderController3 != null) {
            brightnessSliderController3.updateSliderHeight();
        }
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void updateHeightMargins() {
        int dimensionPixelSize;
        if (this.mBarRootView == null) {
            return;
        }
        this.mResourcePicker.getClass();
        Context context = this.mContext;
        int i = -1;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, SecQSPanelResourcePicker.getBrightnessBarHeight(context));
        if (!this.mIsOnCollapsedState) {
            this.mBrightnessBarContainer.setBackground(context.getDrawable(R.drawable.sec_large_button_ripple_background));
            int dimensionPixelSize2 = context.getResources().getDimensionPixelSize(R.dimen.bar_top_margin);
            boolean z = QpRune.QUICK_TABLET;
            if (z) {
                dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.brightness_bar_height_tablet);
            } else {
                dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.brightness_bar_height);
            }
            layoutParams.height = dimensionPixelSize;
            layoutParams.topMargin = dimensionPixelSize2;
            if (!z && context.getResources().getConfiguration().orientation == 2) {
                this.mBrightnessBarContainer.setOrientation(0);
                this.mBrightnessBarContainer.setGravity(16);
            } else {
                this.mBrightnessBarContainer.setOrientation(1);
            }
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mSliderContainer.getLayoutParams();
            layoutParams2.height = SecQSPanelResourcePicker.getBrightnessBarHeight(context);
            Resources resources = context.getResources();
            if (!z && !SecQSPanelResourcePicker.isPortrait(context)) {
                i = resources.getDimensionPixelSize(R.dimen.brightness_bar_width);
            }
            layoutParams2.width = i;
            this.mSliderContainer.setLayoutParams(layoutParams2);
            updateTileLayoutSizeMargins();
        } else {
            int quickQSCommonBottomMargin = SecQSPanelResourcePicker.getQuickQSCommonBottomMargin(context);
            this.mBarBottomMargin = quickQSCommonBottomMargin;
            layoutParams.bottomMargin = quickQSCommonBottomMargin;
        }
        this.mBarRootView.setLayoutParams(layoutParams);
        int brightnessIconSize = SecQSPanelResourcePicker.getBrightnessIconSize(context);
        int brightnessIconSize2 = SecQSPanelResourcePicker.getBrightnessIconSize(context);
        int brightnessIconSize3 = SecQSPanelResourcePicker.getBrightnessIconSize(context);
        RelativeLayout relativeLayout = (RelativeLayout) this.mBarRootView.findViewById(R.id.brightness_detail_container);
        ImageView imageView = this.mBrightnessDetailIcon;
        if (imageView != null) {
            RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
            layoutParams3.width = brightnessIconSize3;
            layoutParams3.height = brightnessIconSize3;
            this.mBrightnessDetailIcon.setLayoutParams(layoutParams3);
        }
        if (relativeLayout != null) {
            RelativeLayout.LayoutParams layoutParams4 = (RelativeLayout.LayoutParams) relativeLayout.getLayoutParams();
            layoutParams4.width = brightnessIconSize;
            layoutParams4.height = brightnessIconSize2;
            relativeLayout.setLayoutParams(layoutParams4);
        }
        LottieAnimationView lottieAnimationView = (LottieAnimationView) this.mBarRootView.findViewById(R.id.brightness_icon);
        RelativeLayout.LayoutParams layoutParams5 = (RelativeLayout.LayoutParams) lottieAnimationView.getLayoutParams();
        layoutParams5.width = brightnessIconSize3;
        layoutParams5.height = brightnessIconSize3;
        lottieAnimationView.setLayoutParams(layoutParams5);
        this.mBrightnessSliderController.updateSliderHeight();
        BrightnessMirrorController brightnessMirrorController = this.mBrightnessMirrorController;
        if (brightnessMirrorController != null) {
            brightnessMirrorController.updateIconSize(brightnessIconSize3);
            FrameLayout frameLayout = this.mBrightnessMirrorController.mBrightnessMirror;
            if (frameLayout != null) {
                RelativeLayout relativeLayout2 = (RelativeLayout) frameLayout.findViewById(R.id.brightness_detail_container);
                RelativeLayout.LayoutParams layoutParams6 = (RelativeLayout.LayoutParams) relativeLayout2.getLayoutParams();
                layoutParams6.width = brightnessIconSize;
                layoutParams6.height = brightnessIconSize2;
                relativeLayout2.setLayoutParams(layoutParams6);
            }
        }
    }

    public final void updateTileLayoutSizeMargins() {
        int i;
        int dimensionPixelSize;
        int dimensionPixelSize2;
        int dimensionPixelSize3;
        int dimensionPixelSize4;
        Context context = this.mContext;
        context.getResources();
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mTileLayout.getLayoutParams();
        this.mResourcePicker.getClass();
        boolean z = QpRune.QUICK_TABLET;
        if (!z && !SecQSPanelResourcePicker.isPortrait(context)) {
            i = -2;
        } else {
            i = -1;
        }
        layoutParams.width = i;
        layoutParams.height = SecQSPanelResourcePicker.getBrightnessTileLayoutHeight(context);
        Resources resources = context.getResources();
        if (z) {
            dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.brightness_tile_layout_left_margin_tablet);
        } else {
            dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.brightness_tile_layout_left_margin);
        }
        int brightnessTileLayoutRightMargin = SecQSPanelResourcePicker.getBrightnessTileLayoutRightMargin(context);
        layoutParams.setMarginStart(dimensionPixelSize);
        layoutParams.setMarginEnd(brightnessTileLayoutRightMargin);
        Resources resources2 = context.getResources();
        if (z) {
            dimensionPixelSize2 = resources2.getDimensionPixelSize(R.dimen.brightness_tile_layout_bottom_margin_tablet);
        } else {
            dimensionPixelSize2 = resources2.getDimensionPixelSize(R.dimen.brightness_tile_layout_bottom_margin);
        }
        layoutParams.bottomMargin = dimensionPixelSize2;
        this.mTileLayout.setLayoutParams(layoutParams);
        if (this.mTileLayout.getChildCount() > 1) {
            View childAt = this.mTileLayout.getChildAt(0);
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) childAt.getLayoutParams();
            layoutParams2.setMarginEnd(SecQSPanelResourcePicker.getBrightnessTileLayoutBetweenMargin(context));
            childAt.setLayoutParams(layoutParams2);
            Resources resources3 = context.getResources();
            int panelWidth = SecQSPanelResourcePicker.getPanelWidth(context) - (SecQSPanelResourcePicker.getPanelSidePadding(context) * 2);
            Resources resources4 = context.getResources();
            if (z) {
                dimensionPixelSize3 = resources4.getDimensionPixelSize(R.dimen.brightness_tile_layout_left_margin_tablet);
            } else {
                dimensionPixelSize3 = resources4.getDimensionPixelSize(R.dimen.brightness_tile_layout_left_margin);
            }
            int brightnessTileLayoutRightMargin2 = ((panelWidth - dimensionPixelSize3) - SecQSPanelResourcePicker.getBrightnessTileLayoutRightMargin(context)) - SecQSPanelResourcePicker.getBrightnessTileLayoutBetweenMargin(context);
            Resources resources5 = context.getResources();
            if (z) {
                dimensionPixelSize4 = resources5.getDimensionPixelSize(R.dimen.brightness_slider_min_width_tablet);
            } else {
                dimensionPixelSize4 = resources5.getDimensionPixelSize(R.dimen.brightness_slider_min_width);
            }
            int dimensionPixelSize5 = (((brightnessTileLayoutRightMargin2 - dimensionPixelSize4) - (resources3.getDimensionPixelSize(R.dimen.brightness_tile_icon_size) * 2)) - (resources3.getDimensionPixelSize(R.dimen.brightness_tile_label_start_margin) * 2)) - (resources3.getDimensionPixelSize(R.dimen.brightness_tile_view_padding) * 4);
            TextView textView = ((SecQSTileView) childAt).mLabel;
            double d = dimensionPixelSize5;
            textView.setMaxWidth((int) (0.6d * d));
            ((SecQSTileView) this.mTileLayout.getChildAt(1)).mLabel.setMaxWidth((int) (d * 0.4d));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002e  */
    /* JADX WARN: Removed duplicated region for block: B:18:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateVisibility() {
        /*
            r4 = this;
            java.lang.Class<com.android.systemui.knox.KnoxStateMonitor> r0 = com.android.systemui.knox.KnoxStateMonitor.class
            java.lang.Object r0 = com.android.systemui.Dependency.get(r0)
            com.android.systemui.knox.KnoxStateMonitor r0 = (com.android.systemui.knox.KnoxStateMonitor) r0
            com.android.systemui.knox.KnoxStateMonitorImpl r0 = (com.android.systemui.knox.KnoxStateMonitorImpl) r0
            com.android.systemui.knox.CustomSdkMonitor r0 = r0.mCustomSdkMonitor
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L1d
            int r0 = r0.mKnoxCustomQuickPanelButtons
            r3 = 4
            r0 = r0 & r3
            if (r0 == r3) goto L18
            r0 = r2
            goto L19
        L18:
            r0 = r1
        L19:
            if (r0 == 0) goto L1d
            r0 = r1
            goto L1e
        L1d:
            r0 = r2
        L1e:
            if (r0 == 0) goto L29
            boolean r0 = r4.mIsOnCollapsedState
            if (r0 == 0) goto L2a
            boolean r0 = r4.mIsAllowedOnTop
            if (r0 == 0) goto L29
            goto L2a
        L29:
            r1 = r2
        L2a:
            boolean r0 = r4.mShowing
            if (r0 == r1) goto L31
            r4.showBar(r1)
        L31:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.bar.BrightnessBar.updateVisibility():void");
    }
}
