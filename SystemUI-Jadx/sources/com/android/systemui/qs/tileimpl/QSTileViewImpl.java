package com.android.systemui.qs.tileimpl;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Trace;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import androidx.core.widget.NestedScrollView$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.SeslRecyclerViewFastScroller$$ExternalSyntheticOutline0;
import com.android.settingslib.Utils;
import com.android.systemui.FontSizeUtils;
import com.android.systemui.R;
import com.android.systemui.animation.LaunchableView;
import com.android.systemui.animation.LaunchableViewDelegate;
import com.android.systemui.plugins.qs.QSIconView;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTileView;
import java.util.List;
import java.util.Objects;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public class QSTileViewImpl extends QSTileView implements HeightOverrideable, LaunchableView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final QSIconView _icon;
    public int _position;
    public String accessibilityClass;
    public ImageView chevronView;
    public final int colorActive;
    public Drawable colorBackgroundDrawable;
    public final int colorInactive;
    public final int colorLabelActive;
    public final int colorLabelInactive;
    public final int colorLabelUnavailable;
    public final int colorSecondaryLabelActive;
    public final int colorSecondaryLabelInactive;
    public final int colorSecondaryLabelUnavailable;
    public final int colorUnavailable;
    public ImageView customDrawableView;
    public final int heightOverride;
    public TextView label;
    public IgnorableChildLinearLayout labelContainer;
    public boolean lastDisabledByPolicy;
    public int lastState;
    public CharSequence lastStateDescription;
    public final LaunchableViewDelegate launchableViewDelegate;
    public final int[] locInScreen;
    public int paintColor;
    public RippleDrawable ripple;
    public TextView secondaryLabel;
    public boolean showRippleEffect;
    public ViewGroup sideView;
    public final ValueAnimator singleAnimator;
    public float squishinessFraction;
    public CharSequence stateDescriptionDeltas;
    public boolean tileState;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class StateChangeRunnable implements Runnable {
        public final QSTile.State state;

        public StateChangeRunnable(QSTile.State state) {
            this.state = state;
        }

        public final boolean equals(Object obj) {
            return obj instanceof StateChangeRunnable;
        }

        public final int hashCode() {
            return Reflection.getOrCreateKotlinClass(StateChangeRunnable.class).hashCode();
        }

        @Override // java.lang.Runnable
        public final void run() {
            QSTileViewImpl.this.handleStateChanged(this.state);
        }
    }

    static {
        new Companion(null);
    }

    public QSTileViewImpl(Context context, QSIconView qSIconView) {
        this(context, qSIconView, false, 4, null);
    }

    public boolean animationsEnabled() {
        boolean z;
        if (!isShown()) {
            return false;
        }
        if (getAlpha() == 1.0f) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            return false;
        }
        getLocationOnScreen(this.locInScreen);
        if (this.locInScreen[1] < (-getHeight())) {
            return false;
        }
        return true;
    }

    public final int getBackgroundColorForState(int i, boolean z) {
        if (i != 0 && !z) {
            if (i == 2) {
                return this.colorActive;
            }
            if (i == 1) {
                return this.colorInactive;
            }
            NestedScrollView$$ExternalSyntheticOutline0.m("Invalid state ", i, "QSTileViewImpl");
            return 0;
        }
        return this.colorUnavailable;
    }

    public final List<Integer> getCurrentColors$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        Integer[] numArr = new Integer[4];
        int i = 0;
        numArr[0] = Integer.valueOf(this.paintColor);
        TextView textView = this.label;
        ImageView imageView = null;
        if (textView == null) {
            textView = null;
        }
        numArr[1] = Integer.valueOf(textView.getCurrentTextColor());
        numArr[2] = Integer.valueOf(getSecondaryLabel().getCurrentTextColor());
        ImageView imageView2 = this.chevronView;
        if (imageView2 != null) {
            imageView = imageView2;
        }
        ColorStateList imageTintList = imageView.getImageTintList();
        if (imageTintList != null) {
            i = imageTintList.getDefaultColor();
        }
        numArr[3] = Integer.valueOf(i);
        return CollectionsKt__CollectionsKt.listOf(numArr);
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final int getDetailY() {
        return (getHeight() / 2) + getTop();
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final QSIconView getIcon() {
        return this._icon;
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final View getIconWithBackground() {
        return this._icon;
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final View getLabel() {
        TextView textView = this.label;
        if (textView == null) {
            return null;
        }
        return textView;
    }

    public final int getLabelColorForState(int i, boolean z) {
        if (i != 0 && !z) {
            if (i == 2) {
                return this.colorLabelActive;
            }
            if (i == 1) {
                return this.colorLabelInactive;
            }
            NestedScrollView$$ExternalSyntheticOutline0.m("Invalid state ", i, "QSTileViewImpl");
            return 0;
        }
        return this.colorLabelUnavailable;
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final View getLabelContainer() {
        IgnorableChildLinearLayout ignorableChildLinearLayout = this.labelContainer;
        if (ignorableChildLinearLayout == null) {
            return null;
        }
        return ignorableChildLinearLayout;
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final View getSecondaryIcon() {
        return getSideView();
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final TextView getSecondaryLabel() {
        TextView textView = this.secondaryLabel;
        if (textView != null) {
            return textView;
        }
        return null;
    }

    public final int getSecondaryLabelColorForState(int i, boolean z) {
        if (i != 0 && !z) {
            if (i == 2) {
                return this.colorSecondaryLabelActive;
            }
            if (i == 1) {
                return this.colorSecondaryLabelInactive;
            }
            NestedScrollView$$ExternalSyntheticOutline0.m("Invalid state ", i, "QSTileViewImpl");
            return 0;
        }
        return this.colorSecondaryLabelUnavailable;
    }

    public final ViewGroup getSideView() {
        ViewGroup viewGroup = this.sideView;
        if (viewGroup != null) {
            return viewGroup;
        }
        return null;
    }

    public void handleStateChanged(QSTile.State state) {
        boolean z;
        int intValue;
        String str;
        int i;
        int i2;
        boolean z2;
        boolean animationsEnabled = animationsEnabled();
        this.showRippleEffect = state.showRippleEffect;
        if (state.state != 0) {
            z = true;
        } else {
            z = false;
        }
        setClickable(z);
        setLongClickable(state.handlesLongClick);
        this._icon.setIcon(state, animationsEnabled);
        setContentDescription(state.contentDescription);
        StringBuilder sb = new StringBuilder();
        SubtitleArrayMapping subtitleArrayMapping = SubtitleArrayMapping.INSTANCE;
        String str2 = state.spec;
        subtitleArrayMapping.getClass();
        int i3 = R.array.tile_states_default;
        if (str2 == null) {
            intValue = R.array.tile_states_default;
        } else {
            Integer num = (Integer) SubtitleArrayMapping.subtitleIdsMap.get(str2);
            if (num == null) {
                num = Integer.valueOf(R.array.tile_states_default);
            }
            intValue = num.intValue();
        }
        String stateText = state.getStateText(intValue, getResources());
        state.secondaryLabel = state.getSecondaryLabel(stateText);
        if (!TextUtils.isEmpty(stateText)) {
            sb.append(stateText);
        }
        if (state.disabledByPolicy && state.state != 0) {
            sb.append(", ");
            String str3 = state.spec;
            if (str3 != null) {
                Integer num2 = (Integer) SubtitleArrayMapping.subtitleIdsMap.get(str3);
                if (num2 == null) {
                    num2 = Integer.valueOf(R.array.tile_states_default);
                }
                i3 = num2.intValue();
            }
            sb.append(getResources().getStringArray(i3)[0]);
        }
        if (!TextUtils.isEmpty(state.stateDescription)) {
            sb.append(", ");
            sb.append(state.stateDescription);
            int i4 = this.lastState;
            if (i4 != -1 && state.state == i4 && !Intrinsics.areEqual(state.stateDescription, this.lastStateDescription)) {
                this.stateDescriptionDeltas = state.stateDescription;
            }
        }
        setStateDescription(sb.toString());
        this.lastStateDescription = state.stateDescription;
        TextView textView = null;
        if (state.state == 0) {
            str = null;
        } else {
            str = state.expandedAccessibilityClassName;
        }
        this.accessibilityClass = str;
        boolean z3 = state instanceof QSTile.BooleanState;
        if (z3 && this.tileState != (z2 = ((QSTile.BooleanState) state).value)) {
            this.tileState = z2;
        }
        TextView textView2 = this.label;
        if (textView2 == null) {
            textView2 = null;
        }
        if (!Objects.equals(textView2.getText(), state.label)) {
            TextView textView3 = this.label;
            if (textView3 == null) {
                textView3 = null;
            }
            textView3.setText(state.label);
        }
        if (!Objects.equals(getSecondaryLabel().getText(), state.secondaryLabel)) {
            getSecondaryLabel().setText(state.secondaryLabel);
            TextView secondaryLabel = getSecondaryLabel();
            if (TextUtils.isEmpty(state.secondaryLabel)) {
                i2 = 8;
            } else {
                i2 = 0;
            }
            secondaryLabel.setVisibility(i2);
        }
        if (state.state != this.lastState || state.disabledByPolicy != this.lastDisabledByPolicy) {
            this.singleAnimator.cancel();
            if (animationsEnabled) {
                ValueAnimator valueAnimator = this.singleAnimator;
                PropertyValuesHolder[] propertyValuesHolderArr = new PropertyValuesHolder[4];
                propertyValuesHolderArr[0] = QSTileViewImplKt.access$colorValuesHolder("background", this.paintColor, getBackgroundColorForState(state.state, state.disabledByPolicy));
                int[] iArr = new int[2];
                TextView textView4 = this.label;
                if (textView4 == null) {
                    textView4 = null;
                }
                iArr[0] = textView4.getCurrentTextColor();
                iArr[1] = getLabelColorForState(state.state, state.disabledByPolicy);
                propertyValuesHolderArr[1] = QSTileViewImplKt.access$colorValuesHolder("label", iArr);
                propertyValuesHolderArr[2] = QSTileViewImplKt.access$colorValuesHolder("secondaryLabel", getSecondaryLabel().getCurrentTextColor(), getSecondaryLabelColorForState(state.state, state.disabledByPolicy));
                int[] iArr2 = new int[2];
                ImageView imageView = this.chevronView;
                if (imageView == null) {
                    imageView = null;
                }
                ColorStateList imageTintList = imageView.getImageTintList();
                if (imageTintList != null) {
                    i = imageTintList.getDefaultColor();
                } else {
                    i = 0;
                }
                iArr2[0] = i;
                iArr2[1] = getSecondaryLabelColorForState(state.state, state.disabledByPolicy);
                propertyValuesHolderArr[3] = QSTileViewImplKt.access$colorValuesHolder("chevron", iArr2);
                valueAnimator.setValues(propertyValuesHolderArr);
                this.singleAnimator.start();
            } else {
                int backgroundColorForState = getBackgroundColorForState(state.state, state.disabledByPolicy);
                int labelColorForState = getLabelColorForState(state.state, state.disabledByPolicy);
                int secondaryLabelColorForState = getSecondaryLabelColorForState(state.state, state.disabledByPolicy);
                int secondaryLabelColorForState2 = getSecondaryLabelColorForState(state.state, state.disabledByPolicy);
                Drawable drawable = this.colorBackgroundDrawable;
                if (drawable == null) {
                    drawable = null;
                }
                drawable.mutate().setTint(backgroundColorForState);
                this.paintColor = backgroundColorForState;
                TextView textView5 = this.label;
                if (textView5 == null) {
                    textView5 = null;
                }
                textView5.setTextColor(labelColorForState);
                getSecondaryLabel().setTextColor(secondaryLabelColorForState);
                ImageView imageView2 = this.chevronView;
                if (imageView2 == null) {
                    imageView2 = null;
                }
                imageView2.setImageTintList(ColorStateList.valueOf(secondaryLabelColorForState2));
            }
        }
        Drawable drawable2 = state.sideViewCustomDrawable;
        if (drawable2 != null) {
            ImageView imageView3 = this.customDrawableView;
            if (imageView3 == null) {
                imageView3 = null;
            }
            imageView3.setImageDrawable(drawable2);
            ImageView imageView4 = this.customDrawableView;
            if (imageView4 == null) {
                imageView4 = null;
            }
            imageView4.setVisibility(0);
            ImageView imageView5 = this.chevronView;
            if (imageView5 == null) {
                imageView5 = null;
            }
            imageView5.setVisibility(8);
        } else if (z3 && !((QSTile.BooleanState) state).forceExpandIcon) {
            ImageView imageView6 = this.customDrawableView;
            if (imageView6 == null) {
                imageView6 = null;
            }
            imageView6.setImageDrawable(null);
            ImageView imageView7 = this.customDrawableView;
            if (imageView7 == null) {
                imageView7 = null;
            }
            imageView7.setVisibility(8);
            ImageView imageView8 = this.chevronView;
            if (imageView8 == null) {
                imageView8 = null;
            }
            imageView8.setVisibility(8);
        } else {
            ImageView imageView9 = this.customDrawableView;
            if (imageView9 == null) {
                imageView9 = null;
            }
            imageView9.setImageDrawable(null);
            ImageView imageView10 = this.customDrawableView;
            if (imageView10 == null) {
                imageView10 = null;
            }
            imageView10.setVisibility(8);
            ImageView imageView11 = this.chevronView;
            if (imageView11 == null) {
                imageView11 = null;
            }
            imageView11.setVisibility(0);
        }
        TextView textView6 = this.label;
        if (textView6 != null) {
            textView = textView6;
        }
        textView.setEnabled(!state.disabledByPolicy);
        this.lastState = state.state;
        this.lastDisabledByPolicy = state.disabledByPolicy;
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final void init(final QSTile qSTile) {
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.android.systemui.qs.tileimpl.QSTileViewImpl$init$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                QSTile.this.click(this);
            }
        };
        View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() { // from class: com.android.systemui.qs.tileimpl.QSTileViewImpl$init$2
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                QSTile.this.longClick(this);
                return true;
            }
        };
        setOnClickListener(onClickListener);
        setOnLongClickListener(onLongClickListener);
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        TextView textView = this.label;
        ImageView imageView = null;
        if (textView == null) {
            textView = null;
        }
        FontSizeUtils.updateFontSize(R.dimen.qs_tile_text_size, textView);
        FontSizeUtils.updateFontSize(R.dimen.qs_tile_text_size, getSecondaryLabel());
        int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen.qs_icon_size);
        ViewGroup.LayoutParams layoutParams = this._icon.getLayoutParams();
        layoutParams.height = dimensionPixelSize;
        layoutParams.width = dimensionPixelSize;
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.qs_tile_padding);
        setPaddingRelative(getResources().getDimensionPixelSize(R.dimen.qs_tile_start_padding), dimensionPixelSize2, dimensionPixelSize2, dimensionPixelSize2);
        int dimensionPixelSize3 = getResources().getDimensionPixelSize(R.dimen.qs_label_container_margin);
        IgnorableChildLinearLayout ignorableChildLinearLayout = this.labelContainer;
        if (ignorableChildLinearLayout == null) {
            ignorableChildLinearLayout = null;
        }
        ((ViewGroup.MarginLayoutParams) ignorableChildLinearLayout.getLayoutParams()).setMarginStart(dimensionPixelSize3);
        ((ViewGroup.MarginLayoutParams) getSideView().getLayoutParams()).setMarginStart(dimensionPixelSize3);
        ImageView imageView2 = this.chevronView;
        if (imageView2 == null) {
            imageView2 = null;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) imageView2.getLayoutParams();
        marginLayoutParams.height = dimensionPixelSize;
        marginLayoutParams.width = dimensionPixelSize;
        int dimensionPixelSize4 = getResources().getDimensionPixelSize(R.dimen.qs_drawable_end_margin);
        ImageView imageView3 = this.customDrawableView;
        if (imageView3 != null) {
            imageView = imageView3;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) imageView.getLayoutParams();
        marginLayoutParams2.height = dimensionPixelSize;
        marginLayoutParams2.setMarginEnd(dimensionPixelSize4);
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (!TextUtils.isEmpty(this.accessibilityClass)) {
            accessibilityEvent.setClassName(this.accessibilityClass);
        }
        if (accessibilityEvent.getContentChangeTypes() == 64 && this.stateDescriptionDeltas != null) {
            accessibilityEvent.getText().add(this.stateDescriptionDeltas);
            this.stateDescriptionDeltas = null;
        }
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        String str;
        String str2;
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setSelected(false);
        TextView textView = null;
        if (TextUtils.isEmpty(getSecondaryLabel().getText())) {
            TextView textView2 = this.label;
            if (textView2 != null) {
                textView = textView2;
            }
            str = String.valueOf(textView.getText());
        } else {
            TextView textView3 = this.label;
            if (textView3 != null) {
                textView = textView3;
            }
            str = ((Object) textView.getText()) + ", " + ((Object) getSecondaryLabel().getText());
        }
        accessibilityNodeInfo.setText(str);
        if (this.lastDisabledByPolicy) {
            accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK.getId(), getResources().getString(R.string.accessibility_tile_disabled_by_policy_action_description)));
        }
        if (!TextUtils.isEmpty(this.accessibilityClass)) {
            if (this.lastDisabledByPolicy) {
                str2 = "android.widget.Button";
            } else {
                str2 = this.accessibilityClass;
            }
            accessibilityNodeInfo.setClassName(str2);
            if (Intrinsics.areEqual(Switch.class.getName(), this.accessibilityClass)) {
                accessibilityNodeInfo.setChecked(this.tileState);
                accessibilityNodeInfo.setCheckable(true);
                if (isLongClickable()) {
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_LONG_CLICK.getId(), getResources().getString(R.string.accessibility_long_click_tile)));
                }
            }
        }
        if (this._position != -1) {
            accessibilityNodeInfo.setCollectionItemInfo(new AccessibilityNodeInfo.CollectionItemInfo(this._position, 1, 0, 1, false));
        }
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        updateHeight();
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        Trace.traceBegin(4096L, "QSTileViewImpl#onMeasure");
        super.onMeasure(i, i2);
        Trace.endSection();
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final void onStateChanged(QSTile.State state) {
        StateChangeRunnable stateChangeRunnable = new StateChangeRunnable(state.copy());
        removeCallbacks(stateChangeRunnable);
        post(stateChangeRunnable);
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x001b, code lost:
    
        if (r3 == null) goto L18;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setClickable(boolean r3) {
        /*
            r2 = this;
            super.setClickable(r3)
            r0 = 0
            if (r3 == 0) goto L19
            boolean r3 = r2.showRippleEffect
            if (r3 == 0) goto L19
            android.graphics.drawable.RippleDrawable r3 = r2.ripple
            if (r3 != 0) goto Lf
            r3 = r0
        Lf:
            android.graphics.drawable.Drawable r1 = r2.colorBackgroundDrawable
            if (r1 != 0) goto L14
            goto L15
        L14:
            r0 = r1
        L15:
            r0.setCallback(r3)
            goto L1e
        L19:
            android.graphics.drawable.Drawable r3 = r2.colorBackgroundDrawable
            if (r3 != 0) goto L1e
            goto L1f
        L1e:
            r0 = r3
        L1f:
            r2.setBackground(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tileimpl.QSTileViewImpl.setClickable(boolean):void");
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final void setPosition(int i) {
        this._position = i;
    }

    @Override // com.android.systemui.animation.LaunchableView
    public final void setShouldBlockVisibilityChanges(boolean z) {
        this.launchableViewDelegate.setShouldBlockVisibilityChanges(z);
    }

    @Override // android.view.View
    public final void setVisibility(int i) {
        this.launchableViewDelegate.setVisibility(i);
    }

    @Override // android.view.View
    public final String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        sb.append('[');
        int[] iArr = this.locInScreen;
        sb.append(SeslRecyclerViewFastScroller$$ExternalSyntheticOutline0.m("locInScreen=(", iArr[0], ", ", iArr[1], ")"));
        sb.append(", iconView=" + this._icon);
        sb.append(", tileState=" + this.tileState);
        sb.append("]");
        return sb.toString();
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final View updateAccessibilityOrder(View view) {
        int i;
        if (view != null) {
            i = view.getId();
        } else {
            i = 0;
        }
        setAccessibilityTraversalAfter(i);
        return this;
    }

    public final void updateHeight() {
        int i = this.heightOverride;
        if (i == -1) {
            i = getMeasuredHeight();
        }
        setBottom(getTop() + ((int) (i * ((this.squishinessFraction * 0.9f) + 0.1f))));
        setScrollY((i - getHeight()) / 2);
    }

    public /* synthetic */ QSTileViewImpl(Context context, QSIconView qSIconView, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, qSIconView, (i & 4) != 0 ? false : z);
    }

    @Override // com.android.systemui.plugins.qs.QSTileView
    public final View getSecondaryLabel() {
        return getSecondaryLabel();
    }

    public QSTileViewImpl(Context context, QSIconView qSIconView, boolean z) {
        super(context);
        this._icon = qSIconView;
        this._position = -1;
        this.heightOverride = -1;
        this.squishinessFraction = 1.0f;
        this.colorActive = Utils.getColorAttrDefaultColor(android.R.^attr-private.colorProgressBackgroundNormal, context, 0);
        int colorAttrDefaultColor = Utils.getColorAttrDefaultColor(R.attr.offStateColor, context, 0);
        this.colorInactive = colorAttrDefaultColor;
        this.colorUnavailable = Color.argb((int) (Color.alpha(colorAttrDefaultColor) * 0.3f), Color.red(colorAttrDefaultColor), Color.green(colorAttrDefaultColor), Color.blue(colorAttrDefaultColor));
        this.colorLabelActive = Utils.getColorAttrDefaultColor(17957230, context, 0);
        this.colorLabelInactive = Utils.getColorAttrDefaultColor(android.R.attr.textColorPrimary, context, 0);
        this.colorLabelUnavailable = Utils.getColorAttrDefaultColor(android.R.attr.textColorTertiary, context, 0);
        this.colorSecondaryLabelActive = Utils.getColorAttrDefaultColor(android.R.attr.textColorSecondaryInverse, context, 0);
        this.colorSecondaryLabelInactive = Utils.getColorAttrDefaultColor(android.R.attr.textColorSecondary, context, 0);
        this.colorSecondaryLabelUnavailable = Utils.getColorAttrDefaultColor(android.R.attr.textColorTertiary, context, 0);
        this.showRippleEffect = true;
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(350L);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.qs.tileimpl.QSTileViewImpl$singleAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                QSTileViewImpl qSTileViewImpl = QSTileViewImpl.this;
                int intValue = ((Integer) valueAnimator2.getAnimatedValue("background")).intValue();
                int intValue2 = ((Integer) valueAnimator2.getAnimatedValue("label")).intValue();
                int intValue3 = ((Integer) valueAnimator2.getAnimatedValue("secondaryLabel")).intValue();
                int intValue4 = ((Integer) valueAnimator2.getAnimatedValue("chevron")).intValue();
                int i = QSTileViewImpl.$r8$clinit;
                Drawable drawable = qSTileViewImpl.colorBackgroundDrawable;
                ImageView imageView = null;
                if (drawable == null) {
                    drawable = null;
                }
                drawable.mutate().setTint(intValue);
                qSTileViewImpl.paintColor = intValue;
                TextView textView = qSTileViewImpl.label;
                if (textView == null) {
                    textView = null;
                }
                textView.setTextColor(intValue2);
                qSTileViewImpl.getSecondaryLabel().setTextColor(intValue3);
                ImageView imageView2 = qSTileViewImpl.chevronView;
                if (imageView2 != null) {
                    imageView = imageView2;
                }
                imageView.setImageTintList(ColorStateList.valueOf(intValue4));
            }
        });
        this.singleAnimator = valueAnimator;
        this.lastState = -1;
        this.launchableViewDelegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.qs.tileimpl.QSTileViewImpl$launchableViewDelegate$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                super/*android.widget.LinearLayout*/.setVisibility(((Number) obj).intValue());
                return Unit.INSTANCE;
            }
        });
        this.locInScreen = new int[2];
        setId(LinearLayout.generateViewId());
        setOrientation(0);
        setGravity(8388627);
        setImportantForAccessibility(1);
        setClipChildren(false);
        setClipToPadding(false);
        setFocusable(true);
        RippleDrawable rippleDrawable = (RippleDrawable) ((LinearLayout) this).mContext.getDrawable(R.drawable.qs_tile_background);
        this.ripple = rippleDrawable;
        this.colorBackgroundDrawable = rippleDrawable.findDrawableByLayerId(R.id.background);
        RippleDrawable rippleDrawable2 = this.ripple;
        setBackground(rippleDrawable2 == null ? null : rippleDrawable2);
        int backgroundColorForState = getBackgroundColorForState(2, false);
        Drawable drawable = this.colorBackgroundDrawable;
        (drawable == null ? null : drawable).mutate().setTint(backgroundColorForState);
        this.paintColor = backgroundColorForState;
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.qs_tile_padding);
        setPaddingRelative(getResources().getDimensionPixelSize(R.dimen.qs_tile_start_padding), dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.qs_icon_size);
        addView(qSIconView, new LinearLayout.LayoutParams(dimensionPixelSize2, dimensionPixelSize2));
        IgnorableChildLinearLayout ignorableChildLinearLayout = (IgnorableChildLinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.qs_tile_label, (ViewGroup) this, false);
        this.labelContainer = ignorableChildLinearLayout;
        this.label = (TextView) ignorableChildLinearLayout.requireViewById(R.id.tile_label);
        IgnorableChildLinearLayout ignorableChildLinearLayout2 = this.labelContainer;
        this.secondaryLabel = (TextView) (ignorableChildLinearLayout2 == null ? null : ignorableChildLinearLayout2).requireViewById(R.id.app_label);
        if (z) {
            IgnorableChildLinearLayout ignorableChildLinearLayout3 = this.labelContainer;
            (ignorableChildLinearLayout3 == null ? null : ignorableChildLinearLayout3).ignoreLastView = true;
            (ignorableChildLinearLayout3 == null ? null : ignorableChildLinearLayout3).forceUnspecifiedMeasure = true;
            getSecondaryLabel().setAlpha(0.0f);
        }
        int labelColorForState = getLabelColorForState(2, false);
        TextView textView = this.label;
        (textView == null ? null : textView).setTextColor(labelColorForState);
        getSecondaryLabel().setTextColor(getSecondaryLabelColorForState(2, false));
        IgnorableChildLinearLayout ignorableChildLinearLayout4 = this.labelContainer;
        addView(ignorableChildLinearLayout4 == null ? null : ignorableChildLinearLayout4);
        this.sideView = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.qs_tile_side_icon, (ViewGroup) this, false);
        this.customDrawableView = (ImageView) getSideView().requireViewById(R.id.customDrawable);
        this.chevronView = (ImageView) getSideView().requireViewById(R.id.chevron);
        int secondaryLabelColorForState = getSecondaryLabelColorForState(2, false);
        ImageView imageView = this.chevronView;
        (imageView != null ? imageView : null).setImageTintList(ColorStateList.valueOf(secondaryLabelColorForState));
        addView(getSideView());
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ void getTILE_STATE_RES_PREFIX$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
        }
    }
}
