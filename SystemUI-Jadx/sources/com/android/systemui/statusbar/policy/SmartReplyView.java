package com.android.systemui.statusbar.policy;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.IndentingPrintWriter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.android.internal.util.ContrastColorUtil;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.R$styleable;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.util.DeviceState;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import noticolorpicker.NotificationColorPicker;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public class SmartReplyView extends ViewGroup implements PanelScreenShotLogger.LogProvider {
    public final BreakIterator mBreakIterator;
    public PriorityQueue mCandidateButtonQueueForSqueezing;
    public int mCurrentBackgroundColor;
    public int mCurrentRippleColor;
    public int mCurrentTextColor;
    public boolean mDidHideSystemReplies;
    public final int mHeightUpperLimit;
    public long mLastDispatchDrawTime;
    public long mLastDrawChildTime;
    public long mLastMeasureTime;
    public int mMaxNumActions;
    public int mMaxSqueezeRemeasureAttempts;
    public int mMinNumSystemGeneratedReplies;
    public boolean mSmartRepliesGeneratedByAssistant;
    public View mSmartReplyContainer;
    public final int mSpacing;
    public int mTotalSqueezeRemeasureAttempts;
    public static final int MEASURE_SPEC_ANY_LENGTH = View.MeasureSpec.makeMeasureSpec(0, 0);
    public static final SmartReplyView$$ExternalSyntheticLambda0 DECREASING_MEASURED_WIDTH_WITHOUT_PADDING_COMPARATOR = new SmartReplyView$$ExternalSyntheticLambda0();

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public static class LayoutParams extends ViewGroup.LayoutParams {
        public SmartButtonType mButtonType;
        public String mNoShowReason;
        public boolean show;
        public int squeezeStatus;

        public /* synthetic */ LayoutParams(int i, int i2, int i3) {
            this(i, i2);
        }

        public boolean isShown() {
            return this.show;
        }

        public /* synthetic */ LayoutParams(Context context, AttributeSet attributeSet, int i) {
            this(context, attributeSet);
        }

        private LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.show = false;
            this.squeezeStatus = 0;
            this.mButtonType = SmartButtonType.REPLY;
            this.mNoShowReason = "new";
        }

        private LayoutParams(int i, int i2) {
            super(i, i2);
            this.show = false;
            this.squeezeStatus = 0;
            this.mButtonType = SmartButtonType.REPLY;
            this.mNoShowReason = "new";
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class SmartActions {
        public final List actions;
        public final boolean fromAssistant;

        public SmartActions(List<Notification.Action> list, boolean z) {
            this.actions = list;
            this.fromAssistant = z;
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public enum SmartButtonType {
        REPLY,
        ACTION
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class SmartReplies {
        public final List choices;
        public final boolean fromAssistant;
        public final PendingIntent pendingIntent;
        public final RemoteInput remoteInput;

        public SmartReplies(List<CharSequence> list, RemoteInput remoteInput, PendingIntent pendingIntent, boolean z) {
            this.choices = list;
            this.remoteInput = remoteInput;
            this.pendingIntent = pendingIntent;
            this.fromAssistant = z;
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class SmartSuggestionMeasures {
        public int mMaxChildHeight;
        public int mMeasuredWidth;

        public SmartSuggestionMeasures(int i, int i2) {
            this.mMeasuredWidth = i;
            this.mMaxChildHeight = i2;
        }

        public final Object clone() {
            return new SmartSuggestionMeasures(this.mMeasuredWidth, this.mMaxChildHeight);
        }
    }

    public SmartReplyView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSmartRepliesGeneratedByAssistant = false;
        this.mHeightUpperLimit = NotificationUtils.getFontScaledHeight(R.dimen.smart_reply_button_max_height, ((ViewGroup) this).mContext);
        int color = context.getColor(R.color.smart_reply_button_background);
        this.mCurrentBackgroundColor = color;
        ((NotificationColorPicker) Dependency.get(NotificationColorPicker.class)).getTextColor(0, false, true);
        ((ViewGroup) this).mContext.getColor(R.color.smart_reply_button_text_dark_bg);
        Color.argb(Color.alpha(((ViewGroup) this).mContext.getColor(R.color.notification_ripple_untinted_color)), 255, 255, 255);
        ContrastColorUtil.calculateContrast(color, color);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SmartReplyView, 0, 0);
        int indexCount = obtainStyledAttributes.getIndexCount();
        int i = 0;
        for (int i2 = 0; i2 < indexCount; i2++) {
            int index = obtainStyledAttributes.getIndex(i2);
            if (index == 1) {
                i = obtainStyledAttributes.getDimensionPixelSize(i2, 0);
            } else if (index == 0) {
                obtainStyledAttributes.getDimensionPixelSize(i2, 0);
            }
        }
        obtainStyledAttributes.recycle();
        this.mSpacing = i;
        this.mBreakIterator = BreakIterator.getLineInstance();
        this.mCandidateButtonQueueForSqueezing = new PriorityQueue(Math.max(getChildCount(), 1), DECREASING_MEASURED_WIDTH_WITHOUT_PADDING_COMPARATOR);
    }

    public static void markButtonsWithPendingSqueezeStatusAs(int i, List list) {
        Iterator it = ((ArrayList) list).iterator();
        while (it.hasNext()) {
            LayoutParams layoutParams = (LayoutParams) ((View) it.next()).getLayoutParams();
            if (layoutParams.squeezeStatus == 1) {
                layoutParams.squeezeStatus = i;
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        this.mLastDispatchDrawTime = SystemClock.elapsedRealtime();
    }

    @Override // android.view.ViewGroup
    public final boolean drawChild(Canvas canvas, View view, long j) {
        if (!((LayoutParams) view.getLayoutParams()).show) {
            return false;
        }
        this.mLastDrawChildTime = SystemClock.elapsedRealtime();
        return super.drawChild(canvas, view, j);
    }

    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        float f;
        float f2;
        indentingPrintWriter.println(this);
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.print("mMaxSqueezeRemeasureAttempts=");
        indentingPrintWriter.println(this.mMaxSqueezeRemeasureAttempts);
        indentingPrintWriter.print("mTotalSqueezeRemeasureAttempts=");
        indentingPrintWriter.println(this.mTotalSqueezeRemeasureAttempts);
        indentingPrintWriter.print("mMaxNumActions=");
        indentingPrintWriter.println(this.mMaxNumActions);
        indentingPrintWriter.print("mSmartRepliesGeneratedByAssistant=");
        indentingPrintWriter.println(this.mSmartRepliesGeneratedByAssistant);
        indentingPrintWriter.print("mMinNumSystemGeneratedReplies=");
        indentingPrintWriter.println(this.mMinNumSystemGeneratedReplies);
        indentingPrintWriter.print("mHeightUpperLimit=");
        indentingPrintWriter.println(this.mHeightUpperLimit);
        indentingPrintWriter.print("mDidHideSystemReplies=");
        indentingPrintWriter.println(this.mDidHideSystemReplies);
        long elapsedRealtime = SystemClock.elapsedRealtime();
        indentingPrintWriter.print("lastMeasureAge (s)=");
        long j = this.mLastMeasureTime;
        float f3 = Float.NaN;
        if (j == 0) {
            f = Float.NaN;
        } else {
            f = ((float) (elapsedRealtime - j)) / 1000.0f;
        }
        indentingPrintWriter.println(f);
        indentingPrintWriter.print("lastDrawChildAge (s)=");
        long j2 = this.mLastDrawChildTime;
        if (j2 == 0) {
            f2 = Float.NaN;
        } else {
            f2 = ((float) (elapsedRealtime - j2)) / 1000.0f;
        }
        indentingPrintWriter.println(f2);
        indentingPrintWriter.print("lastDispatchDrawAge (s)=");
        long j3 = this.mLastDispatchDrawTime;
        if (j3 != 0) {
            f3 = ((float) (elapsedRealtime - j3)) / 1000.0f;
        }
        indentingPrintWriter.println(f3);
        int childCount = getChildCount();
        indentingPrintWriter.print("children: num=");
        indentingPrintWriter.println(childCount);
        indentingPrintWriter.increaseIndent();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            indentingPrintWriter.print("[");
            indentingPrintWriter.print(i);
            indentingPrintWriter.print("] type=");
            indentingPrintWriter.print(layoutParams.mButtonType);
            indentingPrintWriter.print(" squeezeStatus=");
            indentingPrintWriter.print(layoutParams.squeezeStatus);
            indentingPrintWriter.print(" show=");
            indentingPrintWriter.print(layoutParams.show);
            indentingPrintWriter.print(" noShowReason=");
            indentingPrintWriter.print(layoutParams.mNoShowReason);
            indentingPrintWriter.print(" view=");
            indentingPrintWriter.println(childAt);
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.decreaseIndent();
    }

    public final List filterActionsOrReplies(SmartButtonType smartButtonType) {
        ArrayList arrayList = new ArrayList();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            if (childAt.getVisibility() == 0 && (childAt instanceof Button) && layoutParams.mButtonType == smartButtonType) {
                arrayList.add(childAt);
            }
        }
        return arrayList;
    }

    @Override // com.android.systemui.logging.PanelScreenShotLogger.LogProvider
    public final ArrayList gatherState() {
        ArrayList arrayList = new ArrayList();
        int childCount = getChildCount();
        PanelScreenShotLogger panelScreenShotLogger = PanelScreenShotLogger.INSTANCE;
        Integer valueOf = Integer.valueOf(childCount);
        panelScreenShotLogger.getClass();
        PanelScreenShotLogger.addLogItem(arrayList, "childCount", valueOf);
        for (int i = 0; i < childCount; i++) {
            Button button = (Button) getChildAt(i);
            if (button.getText() != null) {
                PanelScreenShotLogger panelScreenShotLogger2 = PanelScreenShotLogger.INSTANCE;
                CharSequence text = button.getText();
                panelScreenShotLogger2.getClass();
                PanelScreenShotLogger.addLogItem(arrayList, "buttonText", text);
                PanelScreenShotLogger.addLogItem(arrayList, "visibility", Integer.valueOf(button.getVisibility()));
                PanelScreenShotLogger.addLogItem(arrayList, "width", Integer.valueOf(button.getWidth()));
                PanelScreenShotLogger.addLogItem(arrayList, "height", Integer.valueOf(button.getHeight()));
                PanelScreenShotLogger.addLogItem(arrayList, "alpha", Float.valueOf(button.getAlpha()));
                arrayList.add("\n");
            }
        }
        PanelScreenShotLogger panelScreenShotLogger3 = PanelScreenShotLogger.INSTANCE;
        String format = String.format("0x%08x", Integer.valueOf(this.mCurrentTextColor));
        panelScreenShotLogger3.getClass();
        PanelScreenShotLogger.addLogItem(arrayList, "currentTextColor", format);
        PanelScreenShotLogger.addLogItem(arrayList, "currentTextColor", String.format("0x%08x", Integer.valueOf(this.mCurrentBackgroundColor)));
        return arrayList;
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        int i = -2;
        return new LayoutParams(i, i, 0);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        boolean z2 = true;
        if (getLayoutDirection() != 1) {
            z2 = false;
        }
        int i7 = i3 - i;
        if (z2) {
            i5 = i7 - ((ViewGroup) this).mPaddingRight;
        } else {
            i5 = ((ViewGroup) this).mPaddingLeft;
        }
        int childCount = getChildCount();
        for (int i8 = 0; i8 < childCount; i8++) {
            View childAt = getChildAt(i8);
            if (((LayoutParams) childAt.getLayoutParams()).show) {
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight = childAt.getMeasuredHeight();
                if (z2) {
                    i6 = i5 - measuredWidth;
                } else {
                    i6 = i5;
                }
                childAt.layout(i6, 0, i6 + measuredWidth, measuredHeight);
                int i9 = measuredWidth + this.mSpacing;
                if (z2) {
                    i5 -= i9;
                } else {
                    i5 += i9;
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:0x01d7, code lost:
    
        r0 = true;
     */
    /* JADX WARN: Removed duplicated region for block: B:146:0x0306  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x0344  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0257  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0268 A[SYNTHETIC] */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onMeasure(int r29, int r30) {
        /*
            Method dump skipped, instructions count: 939
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.SmartReplyView.onMeasure(int, int):void");
    }

    public final void setButtonColors(Button button) {
        Drawable background = button.getBackground();
        if (background instanceof RippleDrawable) {
            Drawable mutate = background.mutate();
            RippleDrawable rippleDrawable = (RippleDrawable) mutate;
            rippleDrawable.setColor(ColorStateList.valueOf(this.mCurrentRippleColor));
            Drawable drawable = rippleDrawable.getDrawable(0);
            if (drawable instanceof InsetDrawable) {
                Drawable drawable2 = ((InsetDrawable) drawable).getDrawable();
                if (drawable2 instanceof GradientDrawable) {
                    ((GradientDrawable) drawable2).setColor(this.mCurrentBackgroundColor);
                }
            }
            button.setBackground(mutate);
        }
        button.setTextColor(this.mCurrentTextColor);
    }

    public final void updateButtonColorOnUiModeChanged() {
        this.mCurrentTextColor = ((NotificationColorPicker) Dependency.get(NotificationColorPicker.class)).getTextColor(0, false, true);
        this.mCurrentRippleColor = ((ViewGroup) this).mContext.getColor(R.color.notification_ripple_untinted_color);
        this.mCurrentBackgroundColor = ((ViewGroup) this).mContext.getColor(R.color.smart_reply_button_background);
        if (DeviceState.isOpenTheme(((ViewGroup) this).mContext)) {
            int color = ((ViewGroup) this).mContext.getColor(R.color.open_theme_notification_title_text_color);
            this.mCurrentTextColor = color;
            this.mCurrentBackgroundColor = Color.argb(127, Color.red(color), Color.green(this.mCurrentTextColor), Color.blue(this.mCurrentTextColor));
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            Button button = (Button) getChildAt(i);
            if (button.getCompoundDrawables() != null && button.getCompoundDrawables()[0] != null) {
                if (button.getCompoundDrawables()[0] instanceof BitmapDrawable) {
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) button.getCompoundDrawables()[0];
                    if (bitmapDrawable.getBitmap().getConfig() == Bitmap.Config.HARDWARE) {
                        bitmapDrawable.setBitmap(bitmapDrawable.getBitmap().copy(Bitmap.Config.ARGB_8888, true));
                        button.setCompoundDrawables(bitmapDrawable, null, null, null);
                    }
                }
                if (ContrastColorUtil.getInstance(getContext()).isGrayscaleIcon(button.getCompoundDrawables()[0])) {
                    button.getCompoundDrawables()[0].setColorFilter(this.mCurrentTextColor, PorterDuff.Mode.SRC_ATOP);
                }
            }
            setButtonColors(button);
        }
    }

    @Override // android.view.ViewGroup
    public final LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(((ViewGroup) this).mContext, attributeSet, 0);
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams.width, layoutParams.height, 0);
    }
}
