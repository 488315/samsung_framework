package com.android.systemui.edgelighting.settings;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.util.Slog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.PathInterpolator;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SeslSeekBar;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.ArrayLinkedVariables$$ExternalSyntheticOutline0;
import androidx.picker.adapter.layoutmanager.AutoFitGridLayoutManager$$ExternalSyntheticOutline0;
import androidx.picker3.app.SeslColorPickerDialog;
import androidx.picker3.widget.SeslColorPicker;
import com.android.systemui.CameraAvailabilityListener$cameraDeviceStateCallback$1$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.edgelighting.Constants;
import com.android.systemui.edgelighting.Feature;
import com.android.systemui.edgelighting.data.EdgeLightingConstants;
import com.android.systemui.edgelighting.data.style.ELPlusStyle;
import com.android.systemui.edgelighting.data.style.EdgeLightingStyle;
import com.android.systemui.edgelighting.data.style.EdgeLightingStyleOption;
import com.android.systemui.edgelighting.effect.container.EdgeLightingDialog;
import com.android.systemui.edgelighting.effect.data.EdgeEffectInfo;
import com.android.systemui.edgelighting.effect.interfaces.IEdgeLightingController;
import com.android.systemui.edgelighting.effect.interfaces.IEdgeLightingWindowCallback;
import com.android.systemui.edgelighting.effectservice.EdgeLightingDispatcher;
import com.android.systemui.edgelighting.effectservice.EffectServiceCollector;
import com.android.systemui.edgelighting.interfaces.IEdgeLightingStyle;
import com.android.systemui.edgelighting.manager.EdgeLightingStyleManager;
import com.android.systemui.edgelighting.utils.DeviceColorMonitor;
import com.android.systemui.edgelighting.utils.EdgeLightingAnalytics;
import com.android.systemui.edgelighting.utils.EdgeLightingSettingUtils;
import com.android.systemui.edgelighting.utils.Utils;
import com.google.android.material.tabs.TabLayout;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public class EdgeLightingStyleActivity extends AppCompatActivity implements View.OnClickListener {
    public static boolean sConfigured = false;
    public static int sFlipFont;
    public LinearLayout mActionBar;
    public Button mApplyBtn;
    public RadioButton mAutoRadioButton;
    public Button mCancelBtn;
    public ColorListAdapter mColorAdapter;
    public GridView mColorGridView;
    public SeslColorPickerDialog mColorPickerDialog;
    public RadioButton mColorRadioButton;
    public LinearLayout mColorTabLayout;
    public IEdgeLightingStyle mCurEdgeStyle;
    public EdgeLightingStyleEffectAdapter mEffectAdapter;
    public GridView mEffectGridView;
    public boolean mIsShowAppIcon;
    public LayoutInflater mLayoutInflater;
    public RelativeLayout mMainRoundedLayout;
    public RelativeLayout mRootLayout;
    public int mSelectedColorIndex;
    public ViewGroup mSubOptionAdvancedLayout;
    public ViewGroup mSubOptionColorLayout;
    public HorizontalScrollView mSubOptionEffectLayout;
    public RelativeLayout mSubOptionRoot;
    public TabLayout mTabLayout;
    public final String TAG = "EdgeLightingStyleActivity";
    public IEdgeLightingController mLightingController = null;
    public final EdgeEffectInfo mPreviewEffectInfo = new EdgeEffectInfo();
    public int mCurThickness = 0;
    public int mCurTransparency = 0;
    public int mCurDuration = 0;
    public boolean mPreviewMode = false;
    public boolean mIsStartByRoutine = false;
    public int mRoutineEffectColor = 0;
    public int mType = -1;
    public int mEdgeLightingEffectColumn = 4;
    public Integer mSelectedColor = null;
    public final AnonymousClass1 mRadioButtonListener = new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.edgelighting.settings.EdgeLightingStyleActivity.1
        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            if (z) {
                EdgeLightingStyleActivity edgeLightingStyleActivity = EdgeLightingStyleActivity.this;
                if (edgeLightingStyleActivity.mColorAdapter != null) {
                    edgeLightingStyleActivity.hidePreviewEdgeLighting();
                    if (compoundButton.getId() == R.id.auto_radio_button) {
                        if (EdgeLightingStyleActivity.this.mColorRadioButton.isChecked()) {
                            EdgeLightingStyleActivity.this.mColorRadioButton.setChecked(false);
                            EdgeLightingStyleActivity.this.mColorRadioButton.setClickable(true);
                        }
                        EdgeLightingStyleActivity edgeLightingStyleActivity2 = EdgeLightingStyleActivity.this;
                        ColorListAdapter colorListAdapter = edgeLightingStyleActivity2.mColorAdapter;
                        colorListAdapter.mAreDisabledAll = true;
                        edgeLightingStyleActivity2.mSelectedColorIndex = 0;
                        colorListAdapter.notifyDataSetChanged();
                        EdgeLightingStyleActivity.this.mAutoRadioButton.setClickable(false);
                        return;
                    }
                    if (compoundButton.getId() == R.id.color_radio_button) {
                        if (EdgeLightingStyleActivity.this.mAutoRadioButton.isChecked()) {
                            EdgeLightingStyleActivity.this.mAutoRadioButton.setChecked(false);
                            EdgeLightingStyleActivity.this.mAutoRadioButton.setClickable(true);
                        }
                        EdgeLightingStyleActivity edgeLightingStyleActivity3 = EdgeLightingStyleActivity.this;
                        edgeLightingStyleActivity3.mColorAdapter.mAreDisabledAll = false;
                        if (!edgeLightingStyleActivity3.mIsStartByRoutine) {
                            edgeLightingStyleActivity3.mSelectedColorIndex = Math.max(EdgeLightingSettingUtils.getEdgeLightingBasicColorIndex(edgeLightingStyleActivity3.getContentResolver()), 3);
                        }
                        EdgeLightingStyleActivity.this.mColorAdapter.notifyDataSetChanged();
                        EdgeLightingStyleActivity.this.mColorRadioButton.setClickable(false);
                    }
                }
            }
        }
    };
    public final AnonymousClass2 mHandler = new Handler(Looper.getMainLooper()) { // from class: com.android.systemui.edgelighting.settings.EdgeLightingStyleActivity.2
        @Override // android.os.Handler
        public final void dispatchMessage(Message message) {
            int i = message.what;
            if (i != 1) {
                if (i == 3) {
                    EdgeLightingStyleActivity edgeLightingStyleActivity = EdgeLightingStyleActivity.this;
                    boolean z = EdgeLightingStyleActivity.sConfigured;
                    edgeLightingStyleActivity.hidePreviewEdgeLighting();
                    return;
                }
                return;
            }
            EdgeLightingStyleActivity edgeLightingStyleActivity2 = EdgeLightingStyleActivity.this;
            boolean z2 = EdgeLightingStyleActivity.sConfigured;
            edgeLightingStyleActivity2.showPreviewEdgeLighting(new int[]{edgeLightingStyleActivity2.getEdgeLightingColor()});
        }
    };
    public final AnonymousClass3 mOnTouchListener = new View.OnTouchListener() { // from class: com.android.systemui.edgelighting.settings.EdgeLightingStyleActivity.3
        @Override // android.view.View.OnTouchListener
        public final boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == 0 && view.getId() == R.id.main_root_layout) {
                Slog.i(EdgeLightingStyleActivity.this.TAG, "empty view clicked");
                EdgeLightingStyleActivity edgeLightingStyleActivity = EdgeLightingStyleActivity.this;
                IEdgeLightingController iEdgeLightingController = edgeLightingStyleActivity.mLightingController;
                if (iEdgeLightingController != null && edgeLightingStyleActivity.mPreviewMode) {
                    edgeLightingStyleActivity.showBottomBarLayout(0);
                } else {
                    if (iEdgeLightingController == null) {
                        edgeLightingStyleActivity.playEdgeLightingByHandler();
                    }
                    final EdgeLightingStyleActivity edgeLightingStyleActivity2 = EdgeLightingStyleActivity.this;
                    if (!edgeLightingStyleActivity2.mPreviewMode) {
                        AnimatorSet animatorSet = edgeLightingStyleActivity2.mAnimatorSet;
                        if (animatorSet != null) {
                            animatorSet.removeAllListeners();
                            edgeLightingStyleActivity2.mAnimatorSet.cancel();
                            edgeLightingStyleActivity2.mAnimatorSet = null;
                        }
                        edgeLightingStyleActivity2.mAnimatorSet = new AnimatorSet();
                        float f = -edgeLightingStyleActivity2.getResources().getDimensionPixelSize(R.dimen.setting_edge_lighting_style_transition_height);
                        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(edgeLightingStyleActivity2.mMainRoundedLayout, "translationY", 0.0f, f);
                        ofFloat.setDuration(300L);
                        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(edgeLightingStyleActivity2.mMainRoundedLayout, "alpha", 1.0f, 0.0f);
                        ofFloat2.setDuration(150L);
                        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(edgeLightingStyleActivity2.mActionBar, "translationY", 0.0f, f);
                        ofFloat3.setDuration(300L);
                        ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(edgeLightingStyleActivity2.mActionBar, "alpha", 1.0f, 0.0f);
                        ofFloat4.setDuration(150L);
                        edgeLightingStyleActivity2.mAnimatorSet.setInterpolator(new PathInterpolator(0.17f, 0.17f, 0.2f, 1.0f));
                        edgeLightingStyleActivity2.mAnimatorSet.playTogether(ofFloat3, ofFloat, ofFloat4, ofFloat2);
                        edgeLightingStyleActivity2.mAnimatorSet.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.edgelighting.settings.EdgeLightingStyleActivity.5
                            @Override // android.animation.Animator.AnimatorListener
                            public final void onAnimationEnd(Animator animator) {
                                EdgeLightingStyleActivity edgeLightingStyleActivity3 = EdgeLightingStyleActivity.this;
                                boolean z = EdgeLightingStyleActivity.sConfigured;
                                edgeLightingStyleActivity3.getWindow().getDecorView().setSystemUiVisibility(edgeLightingStyleActivity3.getWindow().getDecorView().getSystemUiVisibility() | 2 | 2048);
                            }

                            @Override // android.animation.Animator.AnimatorListener
                            public final void onAnimationCancel(Animator animator) {
                            }

                            @Override // android.animation.Animator.AnimatorListener
                            public final void onAnimationRepeat(Animator animator) {
                            }

                            @Override // android.animation.Animator.AnimatorListener
                            public final void onAnimationStart(Animator animator) {
                            }
                        });
                        edgeLightingStyleActivity2.mAnimatorSet.start();
                        edgeLightingStyleActivity2.mPreviewMode = true;
                        edgeLightingStyleActivity2.mMainRoundedLayout.setVisibility(8);
                    }
                }
            }
            return false;
        }
    };
    public final AnonymousClass4 mEdgeLightingCallBack = new IEdgeLightingWindowCallback() { // from class: com.android.systemui.edgelighting.settings.EdgeLightingStyleActivity.4
        @Override // com.android.systemui.edgelighting.effect.interfaces.IEdgeLightingWindowCallback
        public final void onDismissEdgeWindow() {
            EdgeLightingStyleActivity edgeLightingStyleActivity = EdgeLightingStyleActivity.this;
            if (edgeLightingStyleActivity.mPreviewMode) {
                edgeLightingStyleActivity.showBottomBarLayout(0);
            }
        }

        @Override // com.android.systemui.edgelighting.effect.interfaces.IEdgeLightingWindowCallback
        public final void onClickExpandButton(String str) {
        }

        @Override // com.android.systemui.edgelighting.effect.interfaces.IEdgeLightingWindowCallback
        public final void onFlingDownInWindow(boolean z) {
        }

        @Override // com.android.systemui.edgelighting.effect.interfaces.IEdgeLightingWindowCallback
        public final void doActionNotification() {
        }

        @Override // com.android.systemui.edgelighting.effect.interfaces.IEdgeLightingWindowCallback
        public final void onClickToastInWindow() {
        }

        @Override // com.android.systemui.edgelighting.effect.interfaces.IEdgeLightingWindowCallback
        public final void onExtendLightingDuration() {
        }

        @Override // com.android.systemui.edgelighting.effect.interfaces.IEdgeLightingWindowCallback
        public final void onShowEdgeWindow() {
        }

        @Override // com.android.systemui.edgelighting.effect.interfaces.IEdgeLightingWindowCallback
        public final void onSwipeToastInWindow() {
        }

        @Override // com.android.systemui.edgelighting.effect.interfaces.IEdgeLightingWindowCallback
        public final void onFling(boolean z, boolean z2) {
        }
    };
    public AnimatorSet mAnimatorSet = null;
    public final AnonymousClass6 mTabListener = new TabLayout.OnTabSelectedListener() { // from class: com.android.systemui.edgelighting.settings.EdgeLightingStyleActivity.6
        @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
        public final void onTabSelected(TabLayout.Tab tab) {
            String str;
            int i = tab.position;
            EdgeLightingStyleActivity edgeLightingStyleActivity = EdgeLightingStyleActivity.this;
            Slog.d(edgeLightingStyleActivity.TAG, "onTabSelected: position=" + i);
            edgeLightingStyleActivity.showBottomBarOption(i);
            if (i == 0) {
                str = "36014";
            } else if (i == 1) {
                str = "36015";
            } else {
                str = "36016";
            }
            EdgeLightingAnalytics.sendScreenViewLog(str);
        }

        @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
        public final void onTabReselected() {
        }

        @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
        public final void onTabUnselected() {
        }
    };
    public final AnonymousClass10 mColorSetListener = new AnonymousClass10();
    public final AnonymousClass12 mEffectAccessibilityDelegate = new View.AccessibilityDelegate(this) { // from class: com.android.systemui.edgelighting.settings.EdgeLightingStyleActivity.12
        @Override // android.view.View.AccessibilityDelegate
        public final void sendAccessibilityEventUnchecked(View view, AccessibilityEvent accessibilityEvent) {
            if (accessibilityEvent.getEventType() == 16) {
                return;
            }
            super.sendAccessibilityEventUnchecked(view, accessibilityEvent);
        }
    };

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* renamed from: com.android.systemui.edgelighting.settings.EdgeLightingStyleActivity$10, reason: invalid class name */
    /* loaded from: classes.dex */
    public final class AnonymousClass10 implements SeslColorPickerDialog.OnColorSetListener {
        public AnonymousClass10() {
        }

        public final void onColorSet(int i) {
            EdgeLightingStyleActivity edgeLightingStyleActivity = EdgeLightingStyleActivity.this;
            Slog.i(edgeLightingStyleActivity.TAG, "Color set Listener : " + Integer.toHexString(i) + " alpha : " + Color.alpha(i) + " alpha hex: " + Integer.toHexString(Color.alpha(i)));
            if (!edgeLightingStyleActivity.mIsStartByRoutine) {
                Settings.Global.putInt(edgeLightingStyleActivity.getApplicationContext().getContentResolver(), "edgelighting_custom_color", i);
            } else {
                edgeLightingStyleActivity.mRoutineEffectColor = i;
            }
            Context applicationContext = edgeLightingStyleActivity.getApplicationContext();
            String string = Settings.Global.getString(applicationContext.getContentResolver(), "edgelighting_recently_used_color");
            if (string != null && !string.isEmpty()) {
                String[] split = string.split(";");
                int i2 = 6;
                if (split.length < 6) {
                    i2 = split.length;
                }
                String str = "";
                for (int i3 = 0; i3 < i2; i3++) {
                    if (!split[i3].equals(Integer.toString(i))) {
                        str = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(ArrayLinkedVariables$$ExternalSyntheticOutline0.m(str), split[i3], ";");
                    }
                }
                Settings.Global.putString(applicationContext.getContentResolver(), "edgelighting_recently_used_color", i + ";" + str);
            } else {
                Settings.Global.putString(applicationContext.getContentResolver(), "edgelighting_recently_used_color", i + ";");
            }
            edgeLightingStyleActivity.mSelectedColorIndex = 99;
            edgeLightingStyleActivity.mColorAdapter.notifyDataSetChanged();
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* renamed from: com.android.systemui.edgelighting.settings.EdgeLightingStyleActivity$13, reason: invalid class name */
    /* loaded from: classes.dex */
    public abstract /* synthetic */ class AnonymousClass13 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$systemui$edgelighting$data$style$EdgeLightingStyleOption;

        static {
            int[] iArr = new int[EdgeLightingStyleOption.values().length];
            $SwitchMap$com$android$systemui$edgelighting$data$style$EdgeLightingStyleOption = iArr;
            try {
                iArr[EdgeLightingStyleOption.TRANSPARENCY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$systemui$edgelighting$data$style$EdgeLightingStyleOption[EdgeLightingStyleOption.WIDTH.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$systemui$edgelighting$data$style$EdgeLightingStyleOption[EdgeLightingStyleOption.DURATION.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* renamed from: com.android.systemui.edgelighting.settings.EdgeLightingStyleActivity$9, reason: invalid class name */
    /* loaded from: classes.dex */
    public final class AnonymousClass9 {
        public AnonymousClass9() {
        }

        public final void onColorChanged(int i) {
            EdgeLightingStyleActivity edgeLightingStyleActivity = EdgeLightingStyleActivity.this;
            Slog.i(edgeLightingStyleActivity.TAG, "Color changed Listener");
            int[] iArr = {i};
            edgeLightingStyleActivity.mPreviewEffectInfo.mEffectColors = iArr;
            edgeLightingStyleActivity.mSelectedColor = Integer.valueOf(i);
            IEdgeLightingController iEdgeLightingController = edgeLightingStyleActivity.mLightingController;
            if (iEdgeLightingController != null) {
                iEdgeLightingController.updatePreview(edgeLightingStyleActivity.mPreviewEffectInfo);
            } else {
                edgeLightingStyleActivity.mLightingController = new EdgeLightingDialog(edgeLightingStyleActivity);
                edgeLightingStyleActivity.showPreviewEdgeLighting(iArr);
            }
            edgeLightingStyleActivity.hidePreviewEdgeLighting(EdgeLightingSettingUtils.getEdgeLightingDuration(edgeLightingStyleActivity.mCurDuration));
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class ColorListAdapter extends BaseAdapter {
        public boolean mAreDisabledAll;
        public final AnonymousClass1 mOnColorClickListener = new View.OnClickListener() { // from class: com.android.systemui.edgelighting.settings.EdgeLightingStyleActivity.ColorListAdapter.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                if (view.getTag() != null) {
                    int intValue = ((Integer) view.getTag()).intValue();
                    if (intValue == 99) {
                        EdgeLightingStyleActivity edgeLightingStyleActivity = EdgeLightingStyleActivity.this;
                        edgeLightingStyleActivity.mSelectedColor = null;
                        edgeLightingStyleActivity.showColorPickerDialog();
                        return;
                    }
                    EdgeLightingStyleActivity edgeLightingStyleActivity2 = EdgeLightingStyleActivity.this;
                    if (edgeLightingStyleActivity2.mSelectedColorIndex != intValue) {
                        view.setContentDescription(edgeLightingStyleActivity2.getString(R.string.edge_lighting_color_selected_tts));
                        ColorListAdapter colorListAdapter = ColorListAdapter.this;
                        EdgeLightingStyleActivity.this.mSelectedColorIndex = intValue;
                        colorListAdapter.notifyDataSetChanged();
                        Slog.i(EdgeLightingStyleActivity.this.TAG, " onclick select index : " + EdgeLightingStyleActivity.this.mSelectedColorIndex);
                    }
                    EdgeLightingStyleActivity edgeLightingStyleActivity3 = EdgeLightingStyleActivity.this;
                    if (edgeLightingStyleActivity3.mLightingController != null) {
                        EdgeLightingStyleActivity.m1250$$Nest$mupdatePreviewEdgeLighting(edgeLightingStyleActivity3);
                    } else {
                        edgeLightingStyleActivity3.showPreviewEdgeLighting(new int[]{edgeLightingStyleActivity3.getEdgeLightingColor()});
                    }
                    EdgeLightingStyleActivity edgeLightingStyleActivity4 = EdgeLightingStyleActivity.this;
                    edgeLightingStyleActivity4.hidePreviewEdgeLighting(EdgeLightingSettingUtils.getEdgeLightingDuration(edgeLightingStyleActivity4.mCurDuration));
                }
            }
        };

        /* JADX WARN: Type inference failed for: r1v0, types: [com.android.systemui.edgelighting.settings.EdgeLightingStyleActivity$ColorListAdapter$1] */
        public ColorListAdapter() {
            this.mAreDisabledAll = false;
            this.mAreDisabledAll = EdgeLightingStyleActivity.this.mSelectedColorIndex == 0;
        }

        @Override // android.widget.Adapter
        public final int getCount() {
            return 12;
        }

        @Override // android.widget.Adapter
        public final Object getItem(int i) {
            return null;
        }

        @Override // android.widget.Adapter
        public final long getItemId(int i) {
            return 0L;
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public final int getItemViewType(int i) {
            return 0;
        }

        @Override // android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            ColorListViewHolder colorListViewHolder;
            float f;
            int i2;
            AnonymousClass1 anonymousClass1;
            if (view == null) {
                view = EdgeLightingStyleActivity.this.mLayoutInflater.inflate(R.layout.lighting_style_color_item_holder, viewGroup, false);
                colorListViewHolder = new ColorListViewHolder();
                colorListViewHolder.container = (RelativeLayout) view.findViewById(R.id.icon_container);
                colorListViewHolder.iconView = (Button) view.findViewById(R.id.color_icon);
                colorListViewHolder.selectIV = (ImageView) view.findViewById(R.id.color_select_icon);
                view.setTag(colorListViewHolder);
            } else {
                colorListViewHolder = (ColorListViewHolder) view.getTag();
            }
            StringBuilder sb = new StringBuilder();
            if (i == 11) {
                colorListViewHolder.iconView.setBackgroundResource(R.drawable.edge_lighting_setting_color_select);
                sb.append(EdgeLightingStyleActivity.this.getString(R.string.color_picker));
                colorListViewHolder.container.setTag(99);
            } else {
                Drawable drawable = EdgeLightingStyleActivity.this.getDrawable(R.drawable.lighting_style_color_item_icon_bg);
                int i3 = i + 3;
                drawable.setColorFilter(EdgeLightingSettingUtils.getEdgeLightingStylePreDefineColor(EdgeLightingStyleActivity.this.getBaseContext(), i3, false), PorterDuff.Mode.SRC_ATOP);
                colorListViewHolder.iconView.setBackground(drawable);
                colorListViewHolder.container.setTag(Integer.valueOf(i3));
            }
            RelativeLayout relativeLayout = colorListViewHolder.container;
            if (this.mAreDisabledAll) {
                f = 0.4f;
            } else {
                f = 1.0f;
            }
            relativeLayout.setAlpha(f);
            int i4 = EdgeLightingStyleActivity.this.mSelectedColorIndex;
            if (i4 - 3 != i && (i != 11 || i4 != 99)) {
                colorListViewHolder.selectIV.setVisibility(4);
                if (this.mAreDisabledAll) {
                    sb.append(EdgeLightingStyleActivity.this.getString(R.string.edge_lighting_color_disabled_tts));
                } else {
                    sb.append(EdgeLightingStyleActivity.this.getString(R.string.edge_lighting_color_not_selected_tts));
                }
            } else {
                colorListViewHolder.selectIV.setVisibility(0);
                sb.append(EdgeLightingStyleActivity.this.getString(R.string.edge_lighting_color_selected_tts));
            }
            sb.append(" ");
            EdgeLightingStyleActivity edgeLightingStyleActivity = EdgeLightingStyleActivity.this;
            int i5 = i + 3;
            int i6 = i5 - 3;
            int[] iArr = EdgeLightingConstants.DEFAULT_COLOR_NAME_LIST;
            if (i6 >= 0 && i6 < 11) {
                i2 = iArr[i6];
            } else {
                Slog.i("EdgeLightingSettingUtils", "getEdgeLightingStylePreDefineColorResId() Invalid index value : " + i5);
                i2 = iArr[0];
            }
            sb.append(edgeLightingStyleActivity.getString(i2));
            RelativeLayout relativeLayout2 = colorListViewHolder.container;
            if (this.mAreDisabledAll) {
                anonymousClass1 = null;
            } else {
                anonymousClass1 = this.mOnColorClickListener;
            }
            relativeLayout2.setOnClickListener(anonymousClass1);
            colorListViewHolder.container.setContentDescription(sb);
            return view;
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public final int getViewTypeCount() {
            return 1;
        }

        @Override // android.widget.BaseAdapter
        public final void notifyDataSetChanged() {
            EdgeLightingStyleActivity edgeLightingStyleActivity = EdgeLightingStyleActivity.this;
            EdgeLightingStyleActivity.m1249$$Nest$msetDynamicWidth(edgeLightingStyleActivity, edgeLightingStyleActivity.mColorGridView, 6);
            super.notifyDataSetChanged();
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class ColorListViewHolder {
        public RelativeLayout container;
        public Button iconView;
        public ImageView selectIV;
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class EdgeLightingStyleEffectAdapter extends BaseAdapter {
        public final ArrayList mEdgeLightingStyleList = new ArrayList();
        public final AnonymousClass1 mEffectOnClickListener = new View.OnClickListener() { // from class: com.android.systemui.edgelighting.settings.EdgeLightingStyleActivity.EdgeLightingStyleEffectAdapter.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                if (view.getTag() != null) {
                    IEdgeLightingStyle iEdgeLightingStyle = ((EffectListViewHolder) view.getTag()).style;
                    IEdgeLightingStyle iEdgeLightingStyle2 = EdgeLightingStyleActivity.this.mCurEdgeStyle;
                    if (iEdgeLightingStyle2 != null) {
                        if (!iEdgeLightingStyle2.getKey().equals(iEdgeLightingStyle.getKey()) || EdgeLightingStyleActivity.this.mLightingController == null) {
                            EdgeLightingStyleEffectAdapter edgeLightingStyleEffectAdapter = EdgeLightingStyleEffectAdapter.this;
                            EdgeLightingStyleActivity.this.mCurEdgeStyle = iEdgeLightingStyle;
                            edgeLightingStyleEffectAdapter.notifyDataSetChanged();
                            EdgeLightingStyleActivity.this.playEdgeLightingByHandler();
                            EdgeLightingStyleActivity.this.updateTabLayout();
                        }
                    }
                }
            }
        };

        /* JADX WARN: Type inference failed for: r3v2, types: [com.android.systemui.edgelighting.settings.EdgeLightingStyleActivity$EdgeLightingStyleEffectAdapter$1] */
        public EdgeLightingStyleEffectAdapter() {
            EdgeLightingStyleManager edgeLightingStyleManager = EdgeLightingStyleManager.getInstance();
            edgeLightingStyleManager.getClass();
            Iterator it = new ArrayList(edgeLightingStyleManager.mStyleHashMap.values()).iterator();
            while (it.hasNext()) {
                EdgeLightingStyle edgeLightingStyle = (EdgeLightingStyle) it.next();
                if (edgeLightingStyle.mIsSupport && (!EdgeLightingStyleActivity.this.mIsStartByRoutine || EdgeLightingStyleManager.isSupportEffectForRoutine(edgeLightingStyle.mKey))) {
                    this.mEdgeLightingStyleList.add(edgeLightingStyle);
                }
            }
        }

        @Override // android.widget.BaseAdapter, android.widget.ListAdapter
        public final boolean areAllItemsEnabled() {
            return false;
        }

        @Override // android.widget.Adapter
        public final int getCount() {
            return this.mEdgeLightingStyleList.size();
        }

        @Override // android.widget.Adapter
        public final Object getItem(int i) {
            return null;
        }

        @Override // android.widget.Adapter
        public final long getItemId(int i) {
            return 0L;
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public final int getItemViewType(int i) {
            return 0;
        }

        @Override // android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            EffectListViewHolder effectListViewHolder;
            if (view == null) {
                view = EdgeLightingStyleActivity.this.mLayoutInflater.inflate(R.layout.lighting_style_item_holder, viewGroup, false);
                effectListViewHolder = new EffectListViewHolder();
                effectListViewHolder.titleTv = (TextView) view.findViewById(R.id.item_title);
                effectListViewHolder.effectIv = (ImageView) view.findViewById(R.id.item_image);
                effectListViewHolder.checkIv = (ImageView) view.findViewById(R.id.item_image_check);
                view.setTag(effectListViewHolder);
            } else {
                effectListViewHolder = (EffectListViewHolder) view.getTag();
            }
            IEdgeLightingStyle iEdgeLightingStyle = (IEdgeLightingStyle) this.mEdgeLightingStyleList.get(i);
            effectListViewHolder.style = iEdgeLightingStyle;
            effectListViewHolder.titleTv.setText(iEdgeLightingStyle.getTitle(EdgeLightingStyleActivity.this));
            effectListViewHolder.effectIv.setBackground(effectListViewHolder.style.getRoundedIcon(EdgeLightingStyleActivity.this));
            IEdgeLightingStyle iEdgeLightingStyle2 = EdgeLightingStyleActivity.this.mCurEdgeStyle;
            if (iEdgeLightingStyle2 != null && iEdgeLightingStyle2.getKey().equals(effectListViewHolder.style.getKey())) {
                effectListViewHolder.checkIv.setVisibility(0);
                effectListViewHolder.titleTv.setActivated(true);
                view.setContentDescription(EdgeLightingStyleActivity.this.getString(R.string.edge_lighting_color_selected_tts) + " " + ((Object) effectListViewHolder.titleTv.getText()));
            } else {
                effectListViewHolder.checkIv.setVisibility(4);
                effectListViewHolder.titleTv.setActivated(false);
                view.setContentDescription(EdgeLightingStyleActivity.this.getString(R.string.edge_lighting_color_not_selected_tts) + " " + ((Object) effectListViewHolder.titleTv.getText()));
            }
            view.setOnClickListener(this.mEffectOnClickListener);
            view.setAccessibilityDelegate(EdgeLightingStyleActivity.this.mEffectAccessibilityDelegate);
            return view;
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public final int getViewTypeCount() {
            return 1;
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public final boolean hasStableIds() {
            return false;
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public final boolean isEmpty() {
            return false;
        }

        @Override // android.widget.BaseAdapter, android.widget.ListAdapter
        public final boolean isEnabled(int i) {
            return false;
        }

        @Override // android.widget.BaseAdapter
        public final void notifyDataSetChanged() {
            EdgeLightingStyleActivity edgeLightingStyleActivity = EdgeLightingStyleActivity.this;
            GridView gridView = edgeLightingStyleActivity.mEffectGridView;
            gridView.setNumColumns(edgeLightingStyleActivity.calculateColumnCount(gridView, getCount()));
            EdgeLightingStyleActivity edgeLightingStyleActivity2 = EdgeLightingStyleActivity.this;
            GridView gridView2 = edgeLightingStyleActivity2.mEffectGridView;
            EdgeLightingStyleActivity.m1249$$Nest$msetDynamicWidth(edgeLightingStyleActivity2, gridView2, edgeLightingStyleActivity2.calculateColumnCount(gridView2, getCount()));
            super.notifyDataSetChanged();
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class EffectListViewHolder {
        public ImageView checkIv;
        public ImageView effectIv;
        public IEdgeLightingStyle style;
        public TextView titleTv;
    }

    /* renamed from: -$$Nest$msetDynamicWidth, reason: not valid java name */
    public static void m1249$$Nest$msetDynamicWidth(EdgeLightingStyleActivity edgeLightingStyleActivity, GridView gridView, int i) {
        ListAdapter adapter;
        View view;
        edgeLightingStyleActivity.getClass();
        if (gridView != null && (adapter = gridView.getAdapter()) != null && (view = adapter.getView(0, null, gridView)) != null) {
            view.measure(0, 0);
            gridView.measure(0, 0);
            int dimensionPixelSize = (edgeLightingStyleActivity.getResources().getDimensionPixelSize(R.dimen.settings_edge_lighting_style_option_effect_start_end_padding) * 2) + ((i - 1) * gridView.getHorizontalSpacing()) + (view.getMeasuredWidth() * i);
            ViewGroup.LayoutParams layoutParams = gridView.getLayoutParams();
            layoutParams.width = dimensionPixelSize;
            gridView.setLayoutParams(layoutParams);
            gridView.requestLayout();
        }
    }

    /* renamed from: -$$Nest$mupdatePreviewEdgeLighting, reason: not valid java name */
    public static void m1250$$Nest$mupdatePreviewEdgeLighting(EdgeLightingStyleActivity edgeLightingStyleActivity) {
        if (edgeLightingStyleActivity.mLightingController != null) {
            int[] iArr = {edgeLightingStyleActivity.getEdgeLightingColor()};
            EdgeEffectInfo edgeEffectInfo = edgeLightingStyleActivity.mPreviewEffectInfo;
            edgeEffectInfo.mEffectColors = iArr;
            edgeEffectInfo.mStrokeAlpha = 1.0f - (edgeLightingStyleActivity.mCurTransparency / 100.0f);
            float edgeLightingStyleWidth = EdgeLightingSettingUtils.getEdgeLightingStyleWidth(edgeLightingStyleActivity.getApplicationContext(), edgeLightingStyleActivity.mCurEdgeStyle.getKey(), edgeLightingStyleActivity.mCurThickness);
            int i = edgeLightingStyleActivity.mCurThickness;
            edgeEffectInfo.mStrokeWidth = edgeLightingStyleWidth;
            edgeEffectInfo.mWidthDepth = i;
            edgeLightingStyleActivity.mPreviewEffectInfo.mIsMultiResolutionSupoorted = false;
            Slog.i(edgeLightingStyleActivity.TAG, " updatePreviewEdgeLighting " + edgeLightingStyleActivity.mPreviewEffectInfo.mStrokeWidth);
            edgeLightingStyleActivity.mLightingController.updatePreview(edgeLightingStyleActivity.mPreviewEffectInfo);
        }
    }

    public final int calculateColumnCount(GridView gridView, int i) {
        if (gridView != null && gridView.getAdapter() != null) {
            int i2 = getResources().getDisplayMetrics().widthPixels;
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mMainRoundedLayout.getLayoutParams();
            if (i2 < getResources().getDimensionPixelOffset(R.dimen.settings_edge_lighting_style_large_screen_point)) {
                layoutParams.width = -1;
            } else {
                i2 = getResources().getDimensionPixelSize(R.dimen.settings_edge_lighting_style_root_view_large_screen_width);
                layoutParams.width = i2;
            }
            View view = gridView.getAdapter().getView(0, null, gridView);
            gridView.measure(0, 0);
            view.measure(0, 0);
            this.mEdgeLightingEffectColumn = (i2 - getResources().getDimensionPixelSize(R.dimen.settings_edge_lighting_style_color_option_custom_color_margin_start)) / (gridView.getHorizontalSpacing() + view.getMeasuredWidth());
        }
        int i3 = this.mEdgeLightingEffectColumn;
        if (i <= i3) {
            return i;
        }
        if (i <= i3 * 2) {
            return i3;
        }
        if (i % 2 == 0) {
            return i / 2;
        }
        return (i / 2) + 1;
    }

    public final int getCustomColor(boolean z) {
        if (z) {
            int i = this.mRoutineEffectColor;
            if (i == 0) {
                return -11761985;
            }
            return i;
        }
        return Settings.Global.getInt(getApplicationContext().getContentResolver(), "edgelighting_custom_color", -11761985);
    }

    public final int getEdgeLightingColor() {
        int i = this.mSelectedColorIndex;
        if (i == 0) {
            return -11761985;
        }
        if (i == 100) {
            return DeviceColorMonitor.getDeviceWallPaperColorIndex(getContentResolver());
        }
        if (i == 99) {
            return getCustomColor(this.mIsStartByRoutine);
        }
        return EdgeLightingSettingUtils.getEdgeLightingStylePreDefineColor(getBaseContext(), this.mSelectedColorIndex, false);
    }

    public final boolean hideBottomBarSubOption(int i) {
        boolean z;
        HorizontalScrollView horizontalScrollView = this.mSubOptionEffectLayout;
        boolean z2 = true;
        if (horizontalScrollView != null && horizontalScrollView.getVisibility() == 0) {
            this.mSubOptionEffectLayout.setVisibility(8);
            z = true;
        } else {
            z = false;
        }
        ViewGroup viewGroup = this.mSubOptionColorLayout;
        if (viewGroup != null && viewGroup.getVisibility() == 0) {
            this.mSubOptionColorLayout.setVisibility(8);
            z = true;
        }
        ViewGroup viewGroup2 = this.mSubOptionAdvancedLayout;
        if (viewGroup2 != null && viewGroup2.getVisibility() == 0) {
            this.mSubOptionAdvancedLayout.setVisibility(8);
        } else {
            z2 = z;
        }
        if (z2 && i >= 0) {
            this.mType = i;
        }
        return z2;
    }

    public final void hidePreviewEdgeLighting(int i) {
        Slog.i(this.TAG, "hidePreviewEdgeLighting " + i);
        if (hasMessages(3)) {
            removeMessages(3);
        }
        sendEmptyMessageDelayed(3, i);
    }

    public final void makeSeekBar(EdgeLightingStyleOption edgeLightingStyleOption, int i, String str, String str2, int i2) {
        ViewGroup viewGroup = (ViewGroup) findViewById(i);
        ((TextView) viewGroup.findViewById(R.id.image_text)).setText(getResources().getText(edgeLightingStyleOption.getTitleStringID()));
        ((TextView) viewGroup.findViewById(R.id.seekbar_left_text)).setText(str);
        ((TextView) viewGroup.findViewById(R.id.seekbar_right_text)).setText(str2);
        SeslSeekBar seslSeekBar = (SeslSeekBar) viewGroup.findViewById(R.id.seekbar);
        seslSeekBar.setTag(edgeLightingStyleOption);
        seslSeekBar.setEnabled(true);
        seslSeekBar.setMode(5);
        seslSeekBar.setMax(i2);
        seslSeekBar.mOnSeekBarChangeListener = new SeslSeekBar.OnSeekBarChangeListener() { // from class: com.android.systemui.edgelighting.settings.EdgeLightingStyleActivity.11
            @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
            public final void onProgressChanged(SeslSeekBar seslSeekBar2, int i3, boolean z) {
                if (z) {
                    EdgeLightingStyleActivity edgeLightingStyleActivity = EdgeLightingStyleActivity.this;
                    if (edgeLightingStyleActivity.mHandler.hasMessages(3)) {
                        edgeLightingStyleActivity.mHandler.removeMessages(3);
                    }
                    int i4 = AnonymousClass13.$SwitchMap$com$android$systemui$edgelighting$data$style$EdgeLightingStyleOption[((EdgeLightingStyleOption) seslSeekBar2.getTag()).ordinal()];
                    if (i4 != 1) {
                        if (i4 != 2) {
                            if (i4 == 3) {
                                edgeLightingStyleActivity.mCurDuration = i3;
                                Utils.setSeekBarContentDescription(edgeLightingStyleActivity.getApplicationContext(), seslSeekBar2, edgeLightingStyleActivity.getResources().getText(R.string.edge_lighting_header_duration));
                            }
                        } else {
                            edgeLightingStyleActivity.mCurThickness = i3;
                            Utils.setSeekBarContentDescription(edgeLightingStyleActivity.getApplicationContext(), seslSeekBar2, edgeLightingStyleActivity.getResources().getText(R.string.edge_lighting_header_width));
                        }
                    } else {
                        edgeLightingStyleActivity.mCurTransparency = i3;
                        Utils.setSeekBarContentDescription(edgeLightingStyleActivity.getApplicationContext(), seslSeekBar2, edgeLightingStyleActivity.getResources().getText(R.string.edge_lighting_transparency));
                    }
                    if (edgeLightingStyleActivity.mLightingController == null) {
                        edgeLightingStyleActivity.showPreviewEdgeLighting(new int[]{edgeLightingStyleActivity.getEdgeLightingColor()});
                    } else {
                        EdgeLightingStyleActivity.m1250$$Nest$mupdatePreviewEdgeLighting(edgeLightingStyleActivity);
                    }
                }
            }

            @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
            public final void onStartTrackingTouch(SeslSeekBar seslSeekBar2) {
                EdgeLightingStyleActivity edgeLightingStyleActivity = EdgeLightingStyleActivity.this;
                if (edgeLightingStyleActivity.mHandler.hasMessages(3)) {
                    edgeLightingStyleActivity.mHandler.removeMessages(3);
                }
            }

            @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
            public final void onStopTrackingTouch(SeslSeekBar seslSeekBar2) {
                EdgeLightingStyleActivity edgeLightingStyleActivity = EdgeLightingStyleActivity.this;
                edgeLightingStyleActivity.hidePreviewEdgeLighting(EdgeLightingSettingUtils.getEdgeLightingDuration(edgeLightingStyleActivity.mCurDuration));
            }
        };
        int i3 = AnonymousClass13.$SwitchMap$com$android$systemui$edgelighting$data$style$EdgeLightingStyleOption[edgeLightingStyleOption.ordinal()];
        if (i3 != 1) {
            if (i3 != 2) {
                if (i3 == 3) {
                    seslSeekBar.setProgress(this.mCurDuration);
                    Utils.setSeekBarContentDescription(this, seslSeekBar, getResources().getText(R.string.edge_lighting_header_duration));
                    return;
                }
                return;
            }
            seslSeekBar.setProgress(this.mCurThickness);
            Utils.setSeekBarContentDescription(this, seslSeekBar, getResources().getText(R.string.edge_lighting_header_width));
            return;
        }
        seslSeekBar.setProgress(this.mCurTransparency);
        Utils.setSeekBarContentDescription(this, seslSeekBar, getResources().getText(R.string.edge_lighting_transparency));
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        if (view.getId() == R.id.style_apply_btn) {
            if (this.mIsStartByRoutine) {
                StringBuilder sb = new StringBuilder();
                sb.append(this.mCurEdgeStyle.getTitle(this));
                StringBuilder sb2 = new StringBuilder();
                sb2.append(this.mCurEdgeStyle.getKey());
                sb2.append(";" + Integer.toString(this.mSelectedColorIndex));
                sb2.append(";" + Integer.toString(this.mCurTransparency));
                sb2.append(";" + Integer.toString(this.mCurThickness));
                sb2.append(";" + Integer.toString(this.mCurDuration));
                sb2.append(";" + Integer.toString(this.mRoutineEffectColor));
                Slog.d(this.TAG, "sendCurrentSettingToRoutine() data=[" + ((Object) sb) + "]");
                Intent intent = new Intent();
                intent.putExtra("label_params", sb.toString());
                intent.putExtra("intent_params", sb2.toString());
                setResult(-1, intent);
                finish();
                return;
            }
            EdgeLightingStyleManager edgeLightingStyleManager = EdgeLightingStyleManager.getInstance();
            ContentResolver contentResolver = getContentResolver();
            String key = this.mCurEdgeStyle.getKey();
            edgeLightingStyleManager.getClass();
            Settings.System.putStringForUser(contentResolver, "edge_lighting_style_type_str", key, -2);
            StringBuilder sb3 = new StringBuilder("EdgeLightingSaveStyleInfo,");
            sb3.append("Effect=" + this.mCurEdgeStyle.getKey());
            if (this.mCurEdgeStyle.isSupportOption(EdgeLightingStyleOption.COLOR)) {
                EdgeLightingSettingUtils.setEdgeLightingBasicColorIndex(getContentResolver(), this.mSelectedColorIndex);
                int i = this.mSelectedColorIndex;
                if (i == 0) {
                    EdgeLightingSettingUtils.setEdgeLightingColorType(getContentResolver(), 1);
                } else if (i == 99) {
                    EdgeLightingSettingUtils.setEdgeLightingColorType(getContentResolver(), 2);
                } else if (i == 100) {
                    EdgeLightingSettingUtils.setEdgeLightingColorType(getContentResolver(), 3);
                } else {
                    EdgeLightingSettingUtils.setEdgeLightingColorType(getContentResolver(), 0);
                }
                sb3.append(",");
                sb3.append("Color=" + this.mSelectedColorIndex);
            }
            if (this.mCurEdgeStyle.isSupportOption(EdgeLightingStyleOption.WIDTH)) {
                Settings.System.putIntForUser(getContentResolver(), "edge_lighting_thickness", this.mCurThickness, -2);
                sb3.append(",");
                sb3.append("Width=" + EdgeLightingSettingUtils.getEdgeLightingWidth(this.mCurThickness, this));
            }
            if (this.mCurEdgeStyle.isSupportOption(EdgeLightingStyleOption.TRANSPARENCY)) {
                Settings.System.putIntForUser(getContentResolver(), "edge_lighting_transparency", this.mCurTransparency, -2);
                sb3.append(",");
                sb3.append("Transparency=" + (1.0f - (this.mCurTransparency / 100.0f)));
            }
            if (this.mCurEdgeStyle.isSupportOption(EdgeLightingStyleOption.DURATION)) {
                Settings.System.putIntForUser(getBaseContext().getContentResolver(), "edge_lighting_duration", this.mCurDuration, -2);
                sb3.append(",");
                sb3.append("Duration=" + EdgeLightingSettingUtils.getEdgeLightingDuration(this.mCurDuration));
            }
            SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences("edgelighting_pref", 0);
            if (sharedPreferences != null) {
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putString("36105", Utils.getEffectEnglishName(this.mCurEdgeStyle.getKey()));
                edit.putString("36106", Utils.getColorName(this.mSelectedColorIndex));
                edit.putInt("36107", this.mCurTransparency / 5);
                edit.putInt("36108", this.mCurThickness);
                edit.putInt("36110", this.mCurDuration);
                edit.apply();
            }
            Slog.d(this.TAG, sb3.toString());
            finish();
            return;
        }
        if (view.getId() == R.id.style_cancel_btn) {
            finish();
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        SeslColorPickerDialog seslColorPickerDialog = this.mColorPickerDialog;
        if (seslColorPickerDialog != null && seslColorPickerDialog.isShowing()) {
            this.mColorPickerDialog.dismiss();
            showColorPickerDialog();
            Integer num = this.mSelectedColor;
            if (num != null) {
                SeslColorPicker seslColorPicker = this.mColorPickerDialog.mColorPicker;
                seslColorPicker.mRecentColorInfo.mNewColor = num;
                seslColorPicker.updateRecentColorLayout();
            }
        }
        updateLayout();
        EdgeLightingStyleEffectAdapter edgeLightingStyleEffectAdapter = this.mEffectAdapter;
        if (edgeLightingStyleEffectAdapter != null) {
            edgeLightingStyleEffectAdapter.notifyDataSetChanged();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        boolean z;
        String str;
        super.onCreate(bundle);
        if (!sConfigured) {
            EdgeLightingAnalytics.initEdgeLightingAnalyticsStates(getApplication());
            sConfigured = true;
        }
        int i = Constants.$r8$clinit;
        this.mIsStartByRoutine = "com.samsung.android.app.routines".equals(getCallingPackage());
        setContentView(R.layout.setting_edge_lighting_style);
        this.mLayoutInflater = LayoutInflater.from(getBaseContext());
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.layoutInDisplayCutoutMode = 3;
        window.setAttributes(attributes);
        window.setNavigationBarColor(EmergencyPhoneWidget.BG_COLOR);
        window.setBackgroundDrawableResource(android.R.color.black);
        if (Settings.System.getInt(getBaseContext().getContentResolver(), "show_notification_app_icon", 0) == 1) {
            z = true;
        } else {
            z = false;
        }
        this.mIsShowAppIcon = z;
        String edgeLightingStyleType = EdgeLightingStyleManager.getInstance().getEdgeLightingStyleType(getContentResolver());
        if (edgeLightingStyleType != null) {
            this.mCurEdgeStyle = (EdgeLightingStyle) EdgeLightingStyleManager.getInstance().mStyleHashMap.get(edgeLightingStyleType);
        }
        EdgeLightingSettingUtils.initColorTypeIndex(getBaseContext());
        this.mSelectedColorIndex = EdgeLightingSettingUtils.getEdgeLightingBasicColorIndex(getContentResolver());
        this.mCurThickness = Settings.System.getIntForUser(getContentResolver(), "edge_lighting_thickness", 0, -2);
        this.mCurTransparency = Settings.System.getIntForUser(getContentResolver(), "edge_lighting_transparency", 0, -2);
        this.mCurDuration = EdgeLightingSettingUtils.loadEdgeLightingDurationOptionType(getBaseContext());
        if (this.mIsStartByRoutine) {
            Intent intent = getIntent();
            if (intent != null) {
                str = intent.getStringExtra("intent_params");
            } else {
                str = null;
            }
            if (str != null) {
                String[] split = str.split(";");
                String str2 = split[0];
                int parseInt = Integer.parseInt(split[1]);
                int parseInt2 = Integer.parseInt(split[2]);
                int parseInt3 = Integer.parseInt(split[3]);
                int parseInt4 = Integer.parseInt(split[4]);
                int parseInt5 = Integer.parseInt(split[5]);
                EdgeLightingStyle edgeLightingStyle = (EdgeLightingStyle) EdgeLightingStyleManager.getInstance().mStyleHashMap.get(str2);
                this.mCurEdgeStyle = edgeLightingStyle;
                if (edgeLightingStyle == null) {
                    edgeLightingStyle = EdgeLightingStyleManager.getInstance().getDefalutStyle();
                }
                this.mCurEdgeStyle = edgeLightingStyle;
                this.mSelectedColorIndex = parseInt;
                this.mCurThickness = parseInt3;
                this.mCurTransparency = parseInt2;
                this.mCurDuration = parseInt4;
                this.mRoutineEffectColor = parseInt5;
                String str3 = this.TAG;
                StringBuilder m = CameraAvailabilityListener$cameraDeviceStateCallback$1$$ExternalSyntheticOutline0.m("loadRoutineEdgeLightingSetting() : type=", str2, ",color=", parseInt, ",alpha=");
                AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m(m, parseInt2, ",width=", parseInt3, ",time=");
                m.append(parseInt4);
                m.append(",colorValue=");
                m.append(parseInt5);
                Slog.d(str3, m.toString());
            } else {
                this.mCurEdgeStyle = EdgeLightingStyleManager.getInstance().getDefalutStyle();
                this.mSelectedColorIndex = 3;
                this.mCurThickness = 0;
                this.mCurTransparency = 0;
                this.mCurDuration = 0;
                Slog.d(this.TAG, "loadRoutineEdgeLightingSetting() : set default");
            }
        }
        this.mRootLayout = (RelativeLayout) findViewById(R.id.main_root_layout);
        this.mActionBar = (LinearLayout) findViewById(R.id.action_bar_layout);
        Button button = (Button) findViewById(R.id.style_apply_btn);
        this.mApplyBtn = button;
        button.semSetButtonShapeEnabled(true);
        Button button2 = (Button) findViewById(R.id.style_cancel_btn);
        this.mCancelBtn = button2;
        button2.semSetButtonShapeEnabled(true);
        this.mSubOptionRoot = (RelativeLayout) findViewById(R.id.style_sub_option_detail_layout);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.main_rounded_root);
        this.mMainRoundedLayout = relativeLayout;
        relativeLayout.semSetRoundedCorners(15);
        this.mMainRoundedLayout.semSetRoundedCornerColor(15, getColor(android.R.color.black));
        float dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.setting_edge_lighting_style_transition_height);
        this.mMainRoundedLayout.setAlpha(0.0f);
        this.mMainRoundedLayout.setTranslationY(dimensionPixelSize);
        this.mActionBar.setAlpha(0.0f);
        this.mActionBar.setTranslationY(dimensionPixelSize);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        this.mTabLayout = tabLayout;
        tabLayout.seslSetSubTabStyle();
        this.mTabLayout.addOnTabSelectedListener(this.mTabListener);
        if (this.mTabLayout.getTabAt(0) != null) {
            this.mTabLayout.getTabAt(0).view.findViewById(R.id.indicator).setBackground(getDrawable(R.drawable.custom_sesl_tablayout_subtab_indicator_background_dark));
            this.mTabLayout.getTabAt(0).view.findViewById(R.id.indicator).getBackground().setTint(getColor(R.color.custom_sesl_tablayout_background_color));
        }
        if (this.mTabLayout.getTabAt(1) != null) {
            this.mTabLayout.getTabAt(1).view.findViewById(R.id.indicator).setBackground(getDrawable(R.drawable.custom_sesl_tablayout_subtab_indicator_background_dark));
            this.mTabLayout.getTabAt(1).view.findViewById(R.id.indicator).getBackground().setTint(getColor(R.color.custom_sesl_tablayout_background_color));
        }
        if (this.mTabLayout.getTabAt(2) != null) {
            this.mTabLayout.getTabAt(2).view.findViewById(R.id.indicator).setBackground(getDrawable(R.drawable.custom_sesl_tablayout_subtab_indicator_background_dark));
            this.mTabLayout.getTabAt(2).view.findViewById(R.id.indicator).getBackground().setTint(getColor(R.color.custom_sesl_tablayout_background_color));
        }
        TabLayout tabLayout2 = this.mTabLayout;
        ColorStateList colorStateList = getColorStateList(R.color.custom_sesl_tablayout_subtab_text_color_dark);
        if (tabLayout2.tabTextColors != colorStateList) {
            tabLayout2.tabTextColors = colorStateList;
            int size = tabLayout2.tabs.size();
            for (int i2 = 0; i2 < size; i2++) {
                TabLayout.TabView tabView = ((TabLayout.Tab) tabLayout2.tabs.get(i2)).view;
                if (tabView != null) {
                    tabView.update();
                }
            }
        }
        this.mSubOptionRoot.setOnClickListener(this);
        this.mApplyBtn.setOnClickListener(this);
        this.mCancelBtn.setOnClickListener(this);
        this.mRootLayout.setOnTouchListener(this.mOnTouchListener);
        HorizontalScrollView horizontalScrollView = (HorizontalScrollView) findViewById(R.id.sub_option_effect_list_view);
        this.mSubOptionEffectLayout = horizontalScrollView;
        horizontalScrollView.setOverScrollMode(0);
        GridView gridView = (GridView) findViewById(R.id.sub_option_effect_grid_view);
        this.mEffectGridView = gridView;
        gridView.setNumColumns(4);
        this.mSubOptionColorLayout = (ViewGroup) findViewById(R.id.sub_option_color_layout);
        this.mColorTabLayout = (LinearLayout) findViewById(R.id.color_tab_layout);
        GridView gridView2 = (GridView) findViewById(R.id.sub_option_color_grid_view);
        this.mColorGridView = gridView2;
        gridView2.setSmoothScrollbarEnabled(true);
        this.mColorGridView.setNumColumns(6);
        this.mAutoRadioButton = (RadioButton) findViewById(R.id.auto_radio_button);
        RadioButton radioButton = (RadioButton) findViewById(R.id.color_radio_button);
        this.mColorRadioButton = radioButton;
        radioButton.setOnCheckedChangeListener(this.mRadioButtonListener);
        this.mAutoRadioButton.setOnCheckedChangeListener(this.mRadioButtonListener);
        this.mSubOptionAdvancedLayout = (ViewGroup) findViewById(R.id.sub_option_advanced_layout);
        updateLayout();
        showBottomBarLayout(300);
        showBottomBarOption(0);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onPause() {
        super.onPause();
        if (this.mPreviewMode) {
            showBottomBarLayout(0);
        }
        IEdgeLightingController iEdgeLightingController = this.mLightingController;
        if (iEdgeLightingController != null) {
            iEdgeLightingController.stopPreview();
            this.mLightingController.unRegisterEdgeWindowCallback();
            this.mLightingController = null;
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        int i;
        super.onResume();
        Slog.i(this.TAG, "onResume");
        makeSeekBar(EdgeLightingStyleOption.TRANSPARENCY, R.id.transparency_seekbar, getResources().getString(R.string.edge_transparency_low), getResources().getString(R.string.edge_transparency_high), 60);
        makeSeekBar(EdgeLightingStyleOption.WIDTH, R.id.width_seekbar, getResources().getString(R.string.edge_lighting_thickness_narrow), getResources().getString(R.string.edge_lighting_thickness_wide), 4);
        makeSeekBar(EdgeLightingStyleOption.DURATION, R.id.duration_seekbar, getResources().getString(R.string.edge_lighting_duration_short), getResources().getString(R.string.edge_lighting_duration_long), 2);
        EdgeLightingAnalytics.sendScreenViewLog("36014");
        Configuration configuration = getResources().getConfiguration();
        if (configuration != null && (i = configuration.FlipFont) > 0 && sFlipFont != i) {
            Typeface.setFlipFonts();
            sFlipFont = configuration.FlipFont;
        }
        playEdgeLightingByHandler();
    }

    public final void playEdgeLightingByHandler() {
        sendEmptyMessage(1);
        hidePreviewEdgeLighting(EdgeLightingSettingUtils.getEdgeLightingDuration(this.mCurDuration));
    }

    public final void showBottomBarLayout(int i) {
        this.mMainRoundedLayout.setVisibility(0);
        AnimatorSet animatorSet = this.mAnimatorSet;
        if (animatorSet != null) {
            animatorSet.removeAllListeners();
            this.mAnimatorSet.cancel();
            this.mAnimatorSet = null;
        }
        getWindow().getDecorView().setSystemUiVisibility(getWindow().getDecorView().getSystemUiVisibility() & (-3) & (-2049));
        this.mAnimatorSet = new AnimatorSet();
        float dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.setting_edge_lighting_style_transition_height);
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mMainRoundedLayout, "translationY", dimensionPixelSize, 0.0f);
        ofFloat.setDuration(300L);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.mMainRoundedLayout, "alpha", 0.0f, 1.0f);
        ofFloat2.setDuration(150L);
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(this.mActionBar, "translationY", dimensionPixelSize, 0.0f);
        ofFloat3.setDuration(300L);
        ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(this.mActionBar, "alpha", 0.0f, 1.0f);
        ofFloat4.setDuration(150L);
        this.mAnimatorSet.setStartDelay(i);
        this.mAnimatorSet.setInterpolator(new PathInterpolator(0.17f, 0.17f, 0.2f, 1.0f));
        this.mAnimatorSet.playTogether(ofFloat3, ofFloat, ofFloat4, ofFloat2);
        this.mAnimatorSet.start();
        this.mPreviewMode = false;
    }

    public final void showBottomBarOption(int i) {
        int size;
        int i2;
        int i3;
        updateTabLayout();
        if (i == 0 && this.mType != 0) {
            if (hideBottomBarSubOption(i)) {
                if (this.mEffectAdapter == null) {
                    EdgeLightingStyleEffectAdapter edgeLightingStyleEffectAdapter = new EdgeLightingStyleEffectAdapter();
                    this.mEffectAdapter = edgeLightingStyleEffectAdapter;
                    this.mEffectGridView.setAdapter((ListAdapter) edgeLightingStyleEffectAdapter);
                    this.mEffectAdapter.notifyDataSetChanged();
                    EdgeLightingStyleManager edgeLightingStyleManager = EdgeLightingStyleManager.getInstance();
                    String key = this.mCurEdgeStyle.getKey();
                    boolean z = this.mIsStartByRoutine;
                    edgeLightingStyleManager.getClass();
                    ArrayList arrayList = new ArrayList(edgeLightingStyleManager.mStyleHashMap.values());
                    int i4 = 0;
                    while (true) {
                        if (Feature.FEATURE_SUPPORT_COCKTAIL_COLOR_PHONE_COLOR && !z) {
                            size = edgeLightingStyleManager.mStyleHashMap.size();
                        } else {
                            ArrayList arrayList2 = new ArrayList(edgeLightingStyleManager.mStyleHashMap.values());
                            ArrayList arrayList3 = new ArrayList();
                            Iterator it = arrayList2.iterator();
                            while (it.hasNext()) {
                                EdgeLightingStyle edgeLightingStyle = (EdgeLightingStyle) it.next();
                                if (edgeLightingStyle != null && EdgeLightingStyleManager.isSupportEffectForRoutine(edgeLightingStyle.mKey)) {
                                    arrayList3.add(edgeLightingStyle);
                                }
                            }
                            size = arrayList3.size();
                        }
                        if (i4 < size) {
                            if (((EdgeLightingStyle) arrayList.get(i4)).mKey.equals(key)) {
                                break;
                            } else {
                                i4++;
                            }
                        } else {
                            i4 = -1;
                            break;
                        }
                    }
                    int i5 = i4 + 1;
                    int calculateColumnCount = calculateColumnCount(this.mEffectGridView, this.mEffectAdapter.getCount());
                    if (i5 > calculateColumnCount) {
                        i2 = i5 - calculateColumnCount;
                    } else {
                        i2 = i5;
                    }
                    if (getBaseContext().getResources().getConfiguration().getLayoutDirection() == 1) {
                        i2 = calculateColumnCount - (i5 - 1);
                        if (i5 > calculateColumnCount) {
                            i2 += calculateColumnCount;
                        }
                    }
                    int i6 = i2 - 1;
                    if (i6 >= 0) {
                        i3 = i6;
                    } else {
                        i3 = 0;
                    }
                    final int dimensionPixelOffset = (getResources().getDimensionPixelOffset(R.dimen.settings_edge_lighting_style_effect_option_grid_horizontal_space) * i3) + (getResources().getDimensionPixelOffset(R.dimen.setting_edge_lighting_style_grid_item_select_size) * i6);
                    this.mSubOptionEffectLayout.post(new Runnable() { // from class: com.android.systemui.edgelighting.settings.EdgeLightingStyleActivity.7
                        @Override // java.lang.Runnable
                        public final void run() {
                            EdgeLightingStyleActivity.this.mSubOptionEffectLayout.scrollTo(dimensionPixelOffset, 0);
                        }
                    });
                } else {
                    final int scrollX = this.mSubOptionEffectLayout.getScrollX();
                    this.mSubOptionEffectLayout.post(new Runnable() { // from class: com.android.systemui.edgelighting.settings.EdgeLightingStyleActivity.7
                        @Override // java.lang.Runnable
                        public final void run() {
                            EdgeLightingStyleActivity.this.mSubOptionEffectLayout.scrollTo(scrollX, 0);
                        }
                    });
                }
                this.mSubOptionEffectLayout.setVisibility(0);
                return;
            }
            return;
        }
        if (i == 1 && this.mType != 1) {
            if (hideBottomBarSubOption(i)) {
                this.mSubOptionColorLayout.setVisibility(0);
                if (this.mColorAdapter == null) {
                    this.mColorAdapter = new ColorListAdapter();
                }
                this.mColorGridView.setAdapter((ListAdapter) this.mColorAdapter);
                this.mColorAdapter.notifyDataSetChanged();
                if (this.mIsStartByRoutine) {
                    this.mColorRadioButton.setChecked(true);
                    this.mColorRadioButton.setClickable(false);
                    this.mAutoRadioButton.setEnabled(false);
                    return;
                } else if (this.mSelectedColorIndex == 0) {
                    this.mAutoRadioButton.setChecked(true);
                    return;
                } else {
                    this.mColorRadioButton.setChecked(true);
                    return;
                }
            }
            return;
        }
        if (i == 2 && this.mType != 2 && hideBottomBarSubOption(i)) {
            if (this.mCurEdgeStyle.isSupportOption(EdgeLightingStyleOption.TRANSPARENCY)) {
                findViewById(R.id.transparency_seekbar).setVisibility(0);
            } else {
                findViewById(R.id.transparency_seekbar).setVisibility(8);
            }
            if (this.mCurEdgeStyle.isSupportOption(EdgeLightingStyleOption.WIDTH)) {
                findViewById(R.id.width_seekbar).setVisibility(0);
            } else {
                findViewById(R.id.width_seekbar).setVisibility(8);
            }
            if (this.mCurEdgeStyle.isSupportOption(EdgeLightingStyleOption.DURATION)) {
                findViewById(R.id.duration_seekbar).setVisibility(0);
            } else {
                findViewById(R.id.duration_seekbar).setVisibility(8);
            }
            this.mSubOptionAdvancedLayout.setVisibility(0);
        }
    }

    public final void showColorPickerDialog() {
        int[] iArr;
        int customColor = getCustomColor(this.mIsStartByRoutine);
        String string = Settings.Global.getString(getApplicationContext().getContentResolver(), "edgelighting_recently_used_color");
        if (string != null && !string.isEmpty()) {
            String[] split = string.split(";");
            iArr = new int[split.length];
            for (int i = 0; i < split.length; i++) {
                iArr[i] = Integer.parseInt(split[i]);
            }
        } else {
            iArr = null;
        }
        int[] iArr2 = iArr;
        SeslColorPickerDialog seslColorPickerDialog = new SeslColorPickerDialog(this, this.mColorSetListener, customColor, iArr2, false);
        this.mColorPickerDialog = seslColorPickerDialog;
        seslColorPickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.edgelighting.settings.EdgeLightingStyleActivity.8
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                Slog.i(EdgeLightingStyleActivity.this.TAG, " onDismiss ");
                EdgeLightingStyleActivity.this.hidePreviewEdgeLighting();
            }
        });
        this.mColorPickerDialog.mColorPicker.mOnColorChangedListener = new AnonymousClass9();
        this.mColorPickerDialog.show();
        if (iArr2 != null) {
            if (hasMessages(3)) {
                removeMessages(3);
            }
            int[] iArr3 = {getCustomColor(this.mIsStartByRoutine)};
            IEdgeLightingController iEdgeLightingController = this.mLightingController;
            if (iEdgeLightingController == null) {
                showPreviewEdgeLighting(iArr3);
            } else {
                EdgeEffectInfo edgeEffectInfo = this.mPreviewEffectInfo;
                edgeEffectInfo.mEffectColors = iArr3;
                iEdgeLightingController.updatePreview(edgeEffectInfo);
            }
            hidePreviewEdgeLighting(EdgeLightingSettingUtils.getEdgeLightingDuration(this.mCurDuration));
        }
    }

    public final void showPreviewEdgeLighting(int[] iArr) {
        hidePreviewEdgeLighting();
        EdgeLightingDispatcher edgeLightingDispatcher = new EdgeLightingDispatcher(getBaseContext());
        this.mLightingController = edgeLightingDispatcher;
        edgeLightingDispatcher.setForceSettingValue(this.mCurEdgeStyle.getKey());
        IEdgeLightingStyle iEdgeLightingStyle = null;
        String[] strArr = {getString(R.string.edge_lighting_preview_content_text), null};
        EdgeEffectInfo edgeEffectInfo = this.mPreviewEffectInfo;
        edgeEffectInfo.mText = strArr;
        if (this.mIsShowAppIcon) {
            edgeEffectInfo.mAppIcon = getDrawable(R.drawable.appicon_email);
            this.mPreviewEffectInfo.mIsSupportAppIcon = true;
        } else {
            edgeEffectInfo.mAppIcon = getDrawable(R.drawable.ic_stat_notify_email);
            this.mPreviewEffectInfo.mIsSupportAppIcon = false;
        }
        EdgeEffectInfo edgeEffectInfo2 = this.mPreviewEffectInfo;
        float edgeLightingStyleWidth = EdgeLightingSettingUtils.getEdgeLightingStyleWidth(getApplicationContext(), this.mCurEdgeStyle.getKey(), this.mCurThickness);
        int i = this.mCurThickness;
        edgeEffectInfo2.mStrokeWidth = edgeLightingStyleWidth;
        edgeEffectInfo2.mWidthDepth = i;
        EdgeEffectInfo edgeEffectInfo3 = this.mPreviewEffectInfo;
        edgeEffectInfo3.mEffectColors = iArr;
        edgeEffectInfo3.mStrokeAlpha = 1.0f - (this.mCurTransparency / 100.0f);
        Slog.i(this.TAG, " showPreview : " + this.mCurEdgeStyle.getKey());
        int preloadIndex = EdgeLightingStyleManager.getInstance().getPreloadIndex(this.mCurEdgeStyle.getKey());
        if (preloadIndex == 100) {
            this.mPreviewEffectInfo.mEffectType = preloadIndex;
            EffectServiceCollector effectServiceCollector = EffectServiceCollector.getInstance();
            Object key = this.mCurEdgeStyle.getKey();
            Iterator it = effectServiceCollector.mEdgeLightingStyleList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                IEdgeLightingStyle iEdgeLightingStyle2 = (IEdgeLightingStyle) it.next();
                if (iEdgeLightingStyle2.getKey().equals(key)) {
                    iEdgeLightingStyle = iEdgeLightingStyle2;
                    break;
                }
            }
            if (iEdgeLightingStyle instanceof ELPlusStyle) {
                Uri uri = ((ELPlusStyle) iEdgeLightingStyle).mSpecialEffect;
            }
        } else {
            this.mPreviewEffectInfo.mEffectType = preloadIndex;
        }
        this.mPreviewEffectInfo.mLightingDuration = EdgeLightingSettingUtils.getEdgeLightingDuration(this.mCurDuration);
        this.mPreviewEffectInfo.mIsMultiResolutionSupoorted = false;
        this.mLightingController.registerEdgeWindowCallback(this.mEdgeLightingCallBack);
        this.mLightingController.showPreview(this.mPreviewEffectInfo, !this.mIsStartByRoutine);
        this.mLightingController.isUsingELPlusEffect();
    }

    public final void updateLayout() {
        boolean z;
        int i = getResources().getDisplayMetrics().widthPixels;
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mMainRoundedLayout.getLayoutParams();
        if (i < getResources().getDimensionPixelOffset(R.dimen.settings_edge_lighting_style_large_screen_point)) {
            layoutParams.width = -1;
        } else {
            layoutParams.width = getResources().getDimensionPixelSize(R.dimen.settings_edge_lighting_style_root_view_large_screen_width);
        }
        if (this.mColorTabLayout != null) {
            int i2 = 1;
            if (getResources().getConfiguration().smallestScreenWidthDp < 360) {
                z = true;
            } else {
                z = false;
            }
            FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.mColorTabLayout.getLayoutParams();
            if (getResources().getConfiguration().orientation != 2 && z) {
                i2 = 8388611;
            }
            layoutParams2.gravity = i2;
            this.mColorTabLayout.setLayoutParams(layoutParams2);
        }
    }

    public final void updateTabLayout() {
        boolean z;
        if (this.mCurEdgeStyle.isSupportOption(EdgeLightingStyleOption.COLOR)) {
            if (!this.mIsShowAppIcon || (!"preload/noframe".equals(this.mCurEdgeStyle.getKey()) && !"preload/reflection".equals(this.mCurEdgeStyle.getKey()))) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                ((ViewGroup) this.mTabLayout.getChildAt(0)).getChildAt(1).setVisibility(0);
                if (this.mCurEdgeStyle.isSupportOption(EdgeLightingStyleOption.TRANSPARENCY) && !this.mCurEdgeStyle.isSupportOption(EdgeLightingStyleOption.WIDTH) && !this.mCurEdgeStyle.isSupportOption(EdgeLightingStyleOption.DURATION)) {
                    ((ViewGroup) this.mTabLayout.getChildAt(0)).getChildAt(2).setVisibility(8);
                } else {
                    ((ViewGroup) this.mTabLayout.getChildAt(0)).getChildAt(2).setVisibility(0);
                }
                this.mTabLayout.requestLayout();
            }
        }
        ((ViewGroup) this.mTabLayout.getChildAt(0)).getChildAt(1).setVisibility(8);
        if (this.mCurEdgeStyle.isSupportOption(EdgeLightingStyleOption.TRANSPARENCY)) {
        }
        ((ViewGroup) this.mTabLayout.getChildAt(0)).getChildAt(2).setVisibility(0);
        this.mTabLayout.requestLayout();
    }

    public final void hidePreviewEdgeLighting() {
        IEdgeLightingController iEdgeLightingController = this.mLightingController;
        if (iEdgeLightingController != null) {
            iEdgeLightingController.stopPreview();
            this.mLightingController = null;
        }
    }
}
