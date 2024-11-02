package com.android.systemui.controls.ui;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.Icon;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.service.controls.Control;
import android.service.controls.actions.ControlAction;
import android.service.controls.templates.ControlTemplate;
import android.service.controls.templates.RangeTemplate;
import android.service.controls.templates.StatelessTemplate;
import android.service.controls.templates.TemperatureControlTemplate;
import android.service.controls.templates.ThumbnailTemplate;
import android.service.controls.templates.ToggleRangeTemplate;
import android.service.controls.templates.ToggleTemplate;
import android.util.Log;
import android.util.MathUtils;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.app.animation.Interpolators;
import com.android.internal.graphics.ColorUtils;
import com.android.systemui.BasicRune;
import com.android.systemui.controls.ControlsMetricsLogger;
import com.android.systemui.controls.controller.ControlInfo;
import com.android.systemui.controls.controller.ControlsBindingControllerImpl;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.controls.controller.ControlsProviderLifecycleManager;
import com.android.systemui.controls.ui.RenderInfo;
import com.android.systemui.controls.ui.util.ControlsUtil;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class ControlViewHolder {
    public static final int[] ATTR_DISABLED;
    public static final int[] ATTR_ENABLED;
    public static final Set FORCE_PANEL_DEVICES;
    public GradientDrawable baseLayer;
    public Behavior behavior;
    public final DelayableExecutor bgExecutor;
    public final CanUseIconPredicate canUseIconPredicate;
    public final ImageView chevronIcon;
    public ClipDrawable clipLayer;
    public final Context context;
    public final ControlActionCoordinator controlActionCoordinator;
    public final ControlsController controlsController;
    public final ControlsMetricsLogger controlsMetricsLogger;
    public final int currentUserId;
    public final Lazy customControlViewHolder$delegate;
    public ControlWithState cws;
    public final ImageView icon;
    public boolean isLoading;
    public ControlAction lastAction;
    public Dialog lastChallengeDialog;
    public final ViewGroup layout;
    public CharSequence nextStatusText;
    public final Function0 onDialogCancel;
    public ValueAnimator stateAnimator;
    public final TextView status;
    public Animator statusAnimator;
    public final TextView subtitle;
    public final TextView title;
    public final float toggleBackgroundIntensity;
    public final DelayableExecutor uiExecutor;
    public final int uid;
    public boolean userInteractionInProgress;
    public Dialog visibleDialog;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        FORCE_PANEL_DEVICES = SetsKt__SetsKt.setOf(49, 50);
        ATTR_ENABLED = new int[]{R.attr.state_enabled};
        ATTR_DISABLED = new int[]{-16842910};
    }

    public ControlViewHolder(ViewGroup viewGroup, ControlsController controlsController, DelayableExecutor delayableExecutor, DelayableExecutor delayableExecutor2, ControlActionCoordinator controlActionCoordinator, ControlsMetricsLogger controlsMetricsLogger, int i, int i2) {
        ImageView imageView;
        this.layout = viewGroup;
        this.controlsController = controlsController;
        this.uiExecutor = delayableExecutor;
        this.bgExecutor = delayableExecutor2;
        this.controlActionCoordinator = controlActionCoordinator;
        this.controlsMetricsLogger = controlsMetricsLogger;
        this.uid = i;
        this.currentUserId = i2;
        this.canUseIconPredicate = new CanUseIconPredicate(i2);
        this.toggleBackgroundIntensity = viewGroup.getContext().getResources().getFraction(com.android.systemui.R.fraction.controls_toggle_bg_intensity, 1, 1);
        this.icon = (ImageView) viewGroup.requireViewById(com.android.systemui.R.id.icon);
        TextView textView = (TextView) viewGroup.requireViewById(com.android.systemui.R.id.status);
        this.status = textView;
        this.nextStatusText = "";
        TextView textView2 = (TextView) viewGroup.requireViewById(com.android.systemui.R.id.title);
        this.title = textView2;
        TextView textView3 = (TextView) viewGroup.requireViewById(com.android.systemui.R.id.subtitle);
        this.subtitle = textView3;
        boolean z = BasicRune.CONTROLS_SAMSUNG_STYLE;
        if (z) {
            imageView = null;
        } else {
            imageView = (ImageView) viewGroup.requireViewById(com.android.systemui.R.id.chevron_icon);
        }
        this.chevronIcon = imageView;
        this.context = viewGroup.getContext();
        this.onDialogCancel = new Function0() { // from class: com.android.systemui.controls.ui.ControlViewHolder$onDialogCancel$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ControlViewHolder.this.lastChallengeDialog = null;
                return Unit.INSTANCE;
            }
        };
        this.customControlViewHolder$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.controls.ui.ControlViewHolder$customControlViewHolder$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ControlViewHolder controlViewHolder = ControlViewHolder.this;
                return new CustomControlViewHolder(controlViewHolder.layout, controlViewHolder.icon, controlViewHolder.status, controlViewHolder.title, controlViewHolder.subtitle);
            }
        });
        if (z) {
            Pair initClipLayerAndBaseLayer = getCustomControlViewHolder().initClipLayerAndBaseLayer();
            this.clipLayer = (ClipDrawable) initClipLayerAndBaseLayer.getFirst();
            this.baseLayer = (GradientDrawable) initClipLayerAndBaseLayer.getSecond();
        } else {
            LayerDrawable layerDrawable = (LayerDrawable) viewGroup.getBackground();
            layerDrawable.mutate();
            this.clipLayer = (ClipDrawable) layerDrawable.findDrawableByLayerId(com.android.systemui.R.id.clip_layer);
            this.baseLayer = (GradientDrawable) layerDrawable.findDrawableByLayerId(com.android.systemui.R.id.background);
        }
        textView.setSelected(true);
        if (z) {
            viewGroup.measure(0, 0);
            int measuredWidth = viewGroup.getMeasuredWidth();
            int dimensionPixelSize = viewGroup.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.control_custom_padding);
            int dimensionPixelSize2 = ((measuredWidth - viewGroup.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.control_custom_text_start_margin)) - viewGroup.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.control_custom_text_end_margin)) - (dimensionPixelSize * 2);
            textView2.setMaxWidth(dimensionPixelSize2);
            textView3.setMaxWidth(dimensionPixelSize2);
            textView.setMaxWidth(dimensionPixelSize2);
            ControlsUtil.Companion.getClass();
            ControlsUtil.Companion.updateFontSize(textView3, com.android.systemui.R.dimen.control_custom_text_size, 1.1f);
            ControlsUtil.Companion.updateFontSize(textView2, com.android.systemui.R.dimen.control_custom_text_size, 1.1f);
            ControlsUtil.Companion.updateFontSize(textView, com.android.systemui.R.dimen.control_custom_text_size, 1.1f);
        }
    }

    public final void action(ControlAction controlAction) {
        this.lastAction = controlAction;
        ComponentName componentName = getCws().componentName;
        ControlInfo controlInfo = getCws().ci;
        ControlsControllerImpl controlsControllerImpl = (ControlsControllerImpl) this.controlsController;
        if (controlsControllerImpl.confirmAvailability()) {
            ControlsBindingControllerImpl controlsBindingControllerImpl = (ControlsBindingControllerImpl) controlsControllerImpl.bindingController;
            if (controlsBindingControllerImpl.statefulControlSubscriber == null) {
                Log.w("ControlsBindingControllerImpl", "No actions can occur outside of an active subscription. Ignoring.");
                return;
            }
            ControlsProviderLifecycleManager retrieveLifecycleManager = controlsBindingControllerImpl.retrieveLifecycleManager(componentName);
            retrieveLifecycleManager.getClass();
            retrieveLifecycleManager.invokeOrQueue(new ControlsProviderLifecycleManager.Action(controlInfo.controlId, controlAction));
        }
    }

    public final void animateStatusChange(boolean z, final Function0 function0) {
        Animator animator = this.statusAnimator;
        if (animator != null) {
            animator.cancel();
        }
        if (!z) {
            function0.invoke();
            return;
        }
        boolean z2 = this.isLoading;
        TextView textView = this.status;
        if (z2) {
            function0.invoke();
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(textView, "alpha", 0.45f);
            ofFloat.setRepeatMode(2);
            ofFloat.setRepeatCount(-1);
            ofFloat.setDuration(500L);
            ofFloat.setInterpolator(Interpolators.LINEAR);
            ofFloat.setStartDelay(900L);
            ofFloat.start();
            this.statusAnimator = ofFloat;
            return;
        }
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(textView, "alpha", 0.0f);
        ofFloat2.setDuration(200L);
        Interpolator interpolator = Interpolators.LINEAR;
        ofFloat2.setInterpolator(interpolator);
        ofFloat2.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.controls.ui.ControlViewHolder$animateStatusChange$fadeOut$1$1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator2) {
                Function0.this.invoke();
            }
        });
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(textView, "alpha", 1.0f);
        ofFloat3.setDuration(200L);
        ofFloat3.setInterpolator(interpolator);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(ofFloat2, ofFloat3);
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.controls.ui.ControlViewHolder$animateStatusChange$2$1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator2) {
                ControlViewHolder.this.status.setAlpha(1.0f);
                ControlViewHolder.this.statusAnimator = null;
            }
        });
        animatorSet.start();
        this.statusAnimator = animatorSet;
    }

    public final void applyRenderInfo$frameworks__base__packages__SystemUI__android_common__SystemUI_core(int i, final boolean z, boolean z2) {
        int deviceType;
        boolean z3;
        int color;
        List listOf;
        final int i2;
        int i3;
        int i4;
        ColorStateList color2;
        int color3;
        ColorStateList customColor;
        if (getControlStatus() != 1 && getControlStatus() != 0) {
            deviceType = -1000;
        } else {
            deviceType = getDeviceType();
        }
        RenderInfo.Companion companion = RenderInfo.Companion;
        ComponentName componentName = getCws().componentName;
        companion.getClass();
        Context context = this.context;
        final RenderInfo lookup = RenderInfo.Companion.lookup(context, componentName, deviceType, i);
        final ColorStateList colorStateList = context.getResources().getColorStateList(lookup.foreground, context.getTheme());
        final CharSequence charSequence = this.nextStatusText;
        final Control control = getCws().control;
        if (!Intrinsics.areEqual(charSequence, this.status.getText()) && !BasicRune.CONTROLS_SAMSUNG_STYLE) {
            z3 = z2;
        } else {
            z3 = false;
        }
        animateStatusChange(z3, new Function0() { // from class: com.android.systemui.controls.ui.ControlViewHolder$applyRenderInfo$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Code restructure failed: missing block: B:100:0x01ed, code lost:
            
                if (r0 == null) goto L116;
             */
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Removed duplicated region for block: B:104:0x0200  */
            /* JADX WARN: Removed duplicated region for block: B:41:0x00f3  */
            /* JADX WARN: Type inference failed for: r0v14 */
            /* JADX WARN: Type inference failed for: r0v15, types: [com.airbnb.lottie.LottieAnimationView] */
            /* JADX WARN: Type inference failed for: r0v39 */
            @Override // kotlin.jvm.functions.Function0
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object invoke() {
                /*
                    Method dump skipped, instructions count: 673
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.controls.ui.ControlViewHolder$applyRenderInfo$1.invoke():java.lang.Object");
            }
        });
        boolean z4 = BasicRune.CONTROLS_SAMSUNG_STYLE;
        if (z4) {
            color = context.getResources().getColor(com.android.systemui.R.color.control_custom_default_background, context.getTheme());
        } else {
            color = context.getResources().getColor(com.android.systemui.R.color.control_default_background, context.getTheme());
        }
        if (z) {
            Control control2 = getCws().control;
            if (control2 != null && (customColor = control2.getCustomColor()) != null) {
                color3 = customColor.getColorForState(new int[]{R.attr.state_enabled}, customColor.getDefaultColor());
            } else {
                color3 = context.getResources().getColor(lookup.enabledBackground, context.getTheme());
            }
            listOf = CollectionsKt__CollectionsKt.listOf(Integer.valueOf(color3), 255);
        } else if (z4) {
            listOf = CollectionsKt__CollectionsKt.listOf(Integer.valueOf(color), 0);
        } else {
            listOf = CollectionsKt__CollectionsKt.listOf(Integer.valueOf(context.getResources().getColor(com.android.systemui.R.color.control_default_background, context.getTheme())), 0);
        }
        final int intValue = ((Number) listOf.get(0)).intValue();
        int intValue2 = ((Number) listOf.get(1)).intValue();
        if (this.behavior instanceof ToggleRangeBehavior) {
            i2 = ColorUtils.blendARGB(color, intValue, this.toggleBackgroundIntensity);
        } else {
            i2 = color;
        }
        final Drawable drawable = this.clipLayer.getDrawable();
        if (drawable != null) {
            this.clipLayer.setAlpha(0);
            ValueAnimator valueAnimator = this.stateAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            ViewGroup viewGroup = this.layout;
            if (z3) {
                if ((drawable instanceof GradientDrawable) && (color2 = ((GradientDrawable) drawable).getColor()) != null) {
                    i3 = color2.getDefaultColor();
                } else {
                    i3 = intValue;
                }
                ColorStateList color4 = this.baseLayer.getColor();
                if (color4 != null) {
                    i4 = color4.getDefaultColor();
                } else {
                    i4 = i2;
                }
                final float alpha = viewGroup.getAlpha();
                ValueAnimator ofInt = ValueAnimator.ofInt(this.clipLayer.getAlpha(), intValue2);
                final int i5 = i3;
                final int i6 = i4;
                ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.controls.ui.ControlViewHolder$startBackgroundAnimation$1$1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        int intValue3 = ((Integer) valueAnimator2.getAnimatedValue()).intValue();
                        int blendARGB = ColorUtils.blendARGB(i5, intValue, valueAnimator2.getAnimatedFraction());
                        int blendARGB2 = ColorUtils.blendARGB(i6, i2, valueAnimator2.getAnimatedFraction());
                        float lerp = MathUtils.lerp(alpha, 1.0f, valueAnimator2.getAnimatedFraction());
                        ControlViewHolder controlViewHolder = this;
                        Drawable drawable2 = drawable;
                        Set set = ControlViewHolder.FORCE_PANEL_DEVICES;
                        controlViewHolder.getClass();
                        drawable2.setAlpha(intValue3);
                        if (drawable2 instanceof GradientDrawable) {
                            ((GradientDrawable) drawable2).setColor(blendARGB);
                        }
                        controlViewHolder.baseLayer.setColor(blendARGB2);
                        controlViewHolder.layout.setAlpha(lerp);
                    }
                });
                ofInt.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.controls.ui.ControlViewHolder$startBackgroundAnimation$1$2
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        ControlViewHolder.this.stateAnimator = null;
                    }
                });
                ofInt.setDuration(700L);
                ofInt.setInterpolator(Interpolators.CONTROL_STATE);
                ofInt.start();
                this.stateAnimator = ofInt;
                return;
            }
            drawable.setAlpha(intValue2);
            if (drawable instanceof GradientDrawable) {
                ((GradientDrawable) drawable).setColor(intValue);
            }
            this.baseLayer.setColor(i2);
            viewGroup.setAlpha(1.0f);
        }
    }

    public final Behavior bindBehavior(Behavior behavior, Supplier supplier, int i) {
        Behavior behavior2 = (Behavior) supplier.get();
        if (behavior == null || behavior.getClass() != behavior2.getClass()) {
            behavior2.initialize(this);
            this.layout.setAccessibilityDelegate(null);
            behavior = behavior2;
        }
        behavior.bind(getCws(), i);
        return behavior;
    }

    public final Supplier findBehaviorClass(int i, ControlTemplate controlTemplate, int i2, int i3) {
        boolean z = true;
        if (i != 1) {
            return new Supplier() { // from class: com.android.systemui.controls.ui.ControlViewHolder$findBehaviorClass$1
                @Override // java.util.function.Supplier
                public final Object get() {
                    return new StatusBehavior();
                }
            };
        }
        if (Intrinsics.areEqual(controlTemplate, ControlTemplate.NO_TEMPLATE)) {
            return new Supplier() { // from class: com.android.systemui.controls.ui.ControlViewHolder$findBehaviorClass$2
                @Override // java.util.function.Supplier
                public final Object get() {
                    return new TouchBehavior();
                }
            };
        }
        boolean z2 = BasicRune.CONTROLS_LAYOUT_TYPE;
        if (z2) {
            if (!z2 || i3 != 1) {
                z = false;
            }
            if (z) {
                return new Supplier() { // from class: com.android.systemui.controls.ui.ControlViewHolder$findBehaviorClass$3
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return new DefaultBehavior();
                    }
                };
            }
        }
        if (controlTemplate instanceof ThumbnailTemplate) {
            return new Supplier() { // from class: com.android.systemui.controls.ui.ControlViewHolder$findBehaviorClass$4
                @Override // java.util.function.Supplier
                public final Object get() {
                    return new ThumbnailBehavior(ControlViewHolder.this.currentUserId);
                }
            };
        }
        if (i2 == 50) {
            return new Supplier() { // from class: com.android.systemui.controls.ui.ControlViewHolder$findBehaviorClass$5
                @Override // java.util.function.Supplier
                public final Object get() {
                    return new TouchBehavior();
                }
            };
        }
        if (controlTemplate instanceof ToggleTemplate) {
            return new Supplier() { // from class: com.android.systemui.controls.ui.ControlViewHolder$findBehaviorClass$6
                @Override // java.util.function.Supplier
                public final Object get() {
                    return new ToggleBehavior();
                }
            };
        }
        if (controlTemplate instanceof StatelessTemplate) {
            return new Supplier() { // from class: com.android.systemui.controls.ui.ControlViewHolder$findBehaviorClass$7
                @Override // java.util.function.Supplier
                public final Object get() {
                    return new TouchBehavior();
                }
            };
        }
        if (controlTemplate instanceof ToggleRangeTemplate) {
            return new Supplier() { // from class: com.android.systemui.controls.ui.ControlViewHolder$findBehaviorClass$8
                @Override // java.util.function.Supplier
                public final Object get() {
                    return new ToggleRangeBehavior();
                }
            };
        }
        if (controlTemplate instanceof RangeTemplate) {
            return new Supplier() { // from class: com.android.systemui.controls.ui.ControlViewHolder$findBehaviorClass$9
                @Override // java.util.function.Supplier
                public final Object get() {
                    return new ToggleRangeBehavior();
                }
            };
        }
        if (controlTemplate instanceof TemperatureControlTemplate) {
            return new Supplier() { // from class: com.android.systemui.controls.ui.ControlViewHolder$findBehaviorClass$10
                @Override // java.util.function.Supplier
                public final Object get() {
                    return new TemperatureControlBehavior();
                }
            };
        }
        return new Supplier() { // from class: com.android.systemui.controls.ui.ControlViewHolder$findBehaviorClass$11
            @Override // java.util.function.Supplier
            public final Object get() {
                return new DefaultBehavior();
            }
        };
    }

    public final int getControlStatus() {
        Control control = getCws().control;
        if (control != null) {
            return control.getStatus();
        }
        return 0;
    }

    public final ControlTemplate getControlTemplate() {
        ControlTemplate controlTemplate;
        Control control = getCws().control;
        if (control != null) {
            controlTemplate = control.getControlTemplate();
        } else {
            controlTemplate = null;
        }
        if (controlTemplate == null) {
            return ControlTemplate.NO_TEMPLATE;
        }
        return controlTemplate;
    }

    public final CustomControlViewHolder getCustomControlViewHolder() {
        return (CustomControlViewHolder) this.customControlViewHolder$delegate.getValue();
    }

    public final ControlWithState getCws() {
        ControlWithState controlWithState = this.cws;
        if (controlWithState != null) {
            return controlWithState;
        }
        return null;
    }

    public final int getDeviceType() {
        Control control = getCws().control;
        if (control != null) {
            return control.getDeviceType();
        }
        return getCws().ci.deviceType;
    }

    public final void setErrorStatus() {
        final String string = this.context.getResources().getString(com.android.systemui.R.string.controls_error_failed);
        animateStatusChange(true, new Function0() { // from class: com.android.systemui.controls.ui.ControlViewHolder$setErrorStatus$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ControlViewHolder.this.setStatusText(string, true);
                return Unit.INSTANCE;
            }
        });
    }

    public final void setStatusText(CharSequence charSequence, boolean z) {
        if (z) {
            TextView textView = this.status;
            textView.setAlpha(1.0f);
            textView.setText(charSequence);
            updateContentDescription();
        }
        this.nextStatusText = charSequence;
    }

    public final void updateContentDescription() {
        boolean z = BasicRune.CONTROLS_SAMSUNG_STYLE;
        TextView textView = this.status;
        TextView textView2 = this.subtitle;
        TextView textView3 = this.title;
        ViewGroup viewGroup = this.layout;
        if (z) {
            viewGroup.setContentDescription(((Object) textView2.getText()) + " " + ((Object) textView3.getText()) + " " + ((Object) textView.getText()));
            return;
        }
        viewGroup.setContentDescription(((Object) textView3.getText()) + " " + ((Object) textView2.getText()) + " " + ((Object) textView.getText()));
    }

    public final void updateStatusRow$frameworks__base__packages__SystemUI__android_common__SystemUI_core(boolean z, CharSequence charSequence, Drawable drawable, ColorStateList colorStateList, Control control) {
        int[] iArr;
        Icon customIcon;
        TextView textView = this.status;
        textView.setEnabled(z);
        ImageView imageView = this.icon;
        imageView.setEnabled(z);
        boolean z2 = BasicRune.CONTROLS_SAMSUNG_STYLE;
        ImageView imageView2 = this.chevronIcon;
        if (!z2 && imageView2 != null) {
            imageView2.setEnabled(z);
        }
        if (z2) {
            CustomControlViewHolder customControlViewHolder = getCustomControlViewHolder();
            customControlViewHolder.title.setEnabled(z);
            customControlViewHolder.subtitle.setEnabled(z);
        }
        textView.setText(charSequence);
        updateContentDescription();
        textView.setTextColor(colorStateList);
        if (z2 && control == null) {
            return;
        }
        Unit unit = null;
        if (control != null && (customIcon = control.getCustomIcon()) != null) {
            if (!((Boolean) this.canUseIconPredicate.invoke(customIcon)).booleanValue()) {
                customIcon = null;
            }
            if (customIcon != null) {
                imageView.setImageIcon(customIcon);
                imageView.setImageTintList(customIcon.getTintList());
                unit = Unit.INSTANCE;
            }
        }
        if (unit == null) {
            if (drawable instanceof StateListDrawable) {
                if (imageView.getDrawable() == null || !(imageView.getDrawable() instanceof StateListDrawable)) {
                    imageView.setImageDrawable(drawable);
                }
                if (z) {
                    iArr = ATTR_ENABLED;
                } else {
                    iArr = ATTR_DISABLED;
                }
                imageView.setImageState(iArr, true);
            } else {
                imageView.setImageDrawable(drawable);
            }
            if (getDeviceType() != 52) {
                imageView.setImageTintList(colorStateList);
            }
        }
        if (imageView2 != null) {
            imageView2.setImageTintList(imageView.getImageTintList());
        }
    }

    public final boolean usePanel() {
        if (!FORCE_PANEL_DEVICES.contains(Integer.valueOf(getDeviceType())) && !Intrinsics.areEqual(getControlTemplate(), ControlTemplate.NO_TEMPLATE)) {
            return false;
        }
        return true;
    }
}
