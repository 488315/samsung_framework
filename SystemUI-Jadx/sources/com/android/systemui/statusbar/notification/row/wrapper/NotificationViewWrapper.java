package com.android.systemui.statusbar.notification.row.wrapper;

import android.R;
import android.app.Notification;
import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.NotificationHeaderView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.internal.graphics.ColorUtils;
import com.android.internal.util.ContrastColorUtil;
import com.android.internal.widget.CachingIconView;
import com.android.settingslib.Utils;
import com.android.systemui.statusbar.CrossFadeHelper;
import com.android.systemui.statusbar.TransformableView;
import com.android.systemui.statusbar.notification.FeedbackIcon;
import com.android.systemui.statusbar.notification.NotificationFadeAware;
import com.android.systemui.statusbar.notification.TransformState;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public abstract class NotificationViewWrapper implements TransformableView {
    public int mBackgroundColor;
    public final ExpandableNotificationRow mRow;
    public final View mView;

    public NotificationViewWrapper(Context context, View view, ExpandableNotificationRow expandableNotificationRow) {
        new Rect();
        this.mBackgroundColor = 0;
        this.mView = view;
        this.mRow = expandableNotificationRow;
        onReinflated();
    }

    public static int getBackgroundColor(View view) {
        if (view == null) {
            return 0;
        }
        Drawable background = view.getBackground();
        if (!(background instanceof ColorDrawable)) {
            return 0;
        }
        return ((ColorDrawable) background).getColor();
    }

    public static void invertViewLuminosity(View view) {
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        ColorMatrix colorMatrix2 = new ColorMatrix();
        colorMatrix.setRGB2YUV();
        colorMatrix2.set(new float[]{-1.0f, 0.0f, 0.0f, 0.0f, 255.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f});
        colorMatrix.postConcat(colorMatrix2);
        colorMatrix2.setYUV2RGB();
        colorMatrix.postConcat(colorMatrix2);
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        view.setLayerType(2, paint);
    }

    public static NotificationViewWrapper wrap(Context context, View view, ExpandableNotificationRow expandableNotificationRow) {
        boolean z;
        if (view.getId() == 16909810) {
            if ("bigPicture".equals(view.getTag())) {
                return new NotificationBigPictureTemplateViewWrapper(context, view, expandableNotificationRow);
            }
            if ("bigText".equals(view.getTag())) {
                return new NotificationBigTextTemplateViewWrapper(context, view, expandableNotificationRow);
            }
            if (!"media".equals(view.getTag()) && !"bigMediaNarrow".equals(view.getTag())) {
                if ("messaging".equals(view.getTag())) {
                    return new NotificationMessagingTemplateViewWrapper(context, view, expandableNotificationRow);
                }
                if ("conversation".equals(view.getTag())) {
                    return new NotificationConversationTemplateViewWrapper(context, view, expandableNotificationRow);
                }
                if ("call".equals(view.getTag())) {
                    return new NotificationCallTemplateViewWrapper(context, view, expandableNotificationRow);
                }
                if (expandableNotificationRow.mEntry.mSbn.getNotification().isStyle(Notification.DecoratedCustomViewStyle.class)) {
                    return new NotificationDecoratedCustomViewWrapper(context, view, expandableNotificationRow);
                }
                if (NotificationDecoratedCustomViewWrapper.getWrappedCustomView(view) != null) {
                    z = true;
                } else {
                    z = false;
                }
                if (z) {
                    return new NotificationDecoratedCustomViewWrapper(context, view, expandableNotificationRow);
                }
                return new NotificationTemplateViewWrapper(context, view, expandableNotificationRow);
            }
            return new NotificationMediaTemplateViewWrapper(context, view, expandableNotificationRow);
        }
        if (view instanceof NotificationHeaderView) {
            return new NotificationHeaderViewWrapper(context, view, expandableNotificationRow);
        }
        return new NotificationCustomViewWrapper(context, view, expandableNotificationRow);
    }

    public boolean childrenNeedInversion(int i, ViewGroup viewGroup) {
        if (viewGroup == null) {
            return false;
        }
        int backgroundColor = getBackgroundColor(viewGroup);
        if (Color.alpha(backgroundColor) != 255) {
            backgroundColor = ColorUtils.setAlphaComponent(ContrastColorUtil.compositeColors(backgroundColor, i), 255);
        }
        for (int i2 = 0; i2 < viewGroup.getChildCount(); i2++) {
            View childAt = viewGroup.getChildAt(i2);
            if (childAt instanceof TextView) {
                if (ColorUtils.calculateContrast(((TextView) childAt).getCurrentTextColor(), backgroundColor) < 3.0d) {
                    return true;
                }
            } else if ((childAt instanceof ViewGroup) && childrenNeedInversion(backgroundColor, (ViewGroup) childAt)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.systemui.statusbar.TransformableView
    public TransformState getCurrentState(int i) {
        return null;
    }

    public int getCustomBackgroundColor() {
        if (this.mRow.mIsSummaryWithChildren) {
            return 0;
        }
        return this.mBackgroundColor;
    }

    public View getExpandButton() {
        return null;
    }

    public int getExtraMeasureHeight() {
        return 0;
    }

    public int getHeaderTranslation(boolean z) {
        return 0;
    }

    public CachingIconView getIcon() {
        return null;
    }

    public int getMinLayoutHeight() {
        return 0;
    }

    public NotificationHeaderView getNotificationHeader() {
        return null;
    }

    public int getOriginalIconColor() {
        return 1;
    }

    public View getShelfTransformationTarget() {
        return null;
    }

    public final boolean needsInversion(View view, int i) {
        boolean z;
        boolean z2;
        if (view == null) {
            return false;
        }
        if ((this.mView.getResources().getConfiguration().uiMode & 48) == 32) {
            z = true;
        } else {
            z = false;
        }
        if (!z || this.mRow.mEntry.targetSdk >= 29) {
            return false;
        }
        int backgroundColor = getBackgroundColor(view);
        if (backgroundColor != 0) {
            i = backgroundColor;
        }
        if (i == 0) {
            i = resolveBackgroundColor();
        }
        float[] fArr = {0.0f, 0.0f, 0.0f};
        ColorUtils.colorToHSL(i, fArr);
        float f = fArr[1];
        if (f != 0.0f) {
            return false;
        }
        if (f == 0.0f && fArr[2] > 0.5d) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2) {
            return true;
        }
        if (!(view instanceof ViewGroup)) {
            return false;
        }
        return childrenNeedInversion(i, (ViewGroup) view);
    }

    public final void onReinflated() {
        if (!(this instanceof NotificationCustomViewWrapper)) {
            this.mBackgroundColor = 0;
        }
        View view = this.mView;
        int backgroundColor = getBackgroundColor(view);
        if (backgroundColor != 0) {
            this.mBackgroundColor = backgroundColor;
            view.setBackground(new ColorDrawable(0));
        }
    }

    public final int resolveBackgroundColor() {
        int customBackgroundColor = getCustomBackgroundColor();
        if (customBackgroundColor != 0) {
            return customBackgroundColor;
        }
        return Utils.getColorAttr(R.attr.colorBackground, this.mView.getContext()).getDefaultColor();
    }

    public void setNotificationFaded(boolean z) {
        NotificationFadeAware.setLayerTypeForFaded(getIcon(), z);
        NotificationFadeAware.setLayerTypeForFaded(getExpandButton(), z);
    }

    @Override // com.android.systemui.statusbar.TransformableView
    public void setVisible(boolean z) {
        int i;
        View view = this.mView;
        view.animate().cancel();
        if (z) {
            i = 0;
        } else {
            i = 4;
        }
        view.setVisibility(i);
    }

    public boolean shouldClipToRounding(boolean z) {
        return this instanceof NotificationCustomViewWrapper;
    }

    @Override // com.android.systemui.statusbar.TransformableView
    public void transformFrom(TransformableView transformableView) {
        CrossFadeHelper.fadeIn(this.mView, 210L, 0);
    }

    @Override // com.android.systemui.statusbar.TransformableView
    public void transformTo(TransformableView transformableView, Runnable runnable) {
        CrossFadeHelper.fadeOut(this.mView, 210L, runnable);
    }

    @Override // com.android.systemui.statusbar.TransformableView
    public void transformFrom(float f, TransformableView transformableView) {
        CrossFadeHelper.fadeIn(this.mView, f, true);
    }

    @Override // com.android.systemui.statusbar.TransformableView
    public void transformTo(float f, TransformableView transformableView) {
        CrossFadeHelper.fadeOut(this.mView, f, true);
    }

    public void onContentUpdated(ExpandableNotificationRow expandableNotificationRow) {
    }

    public void setAnimationsRunning(boolean z) {
    }

    public void setFeedbackIcon(FeedbackIcon feedbackIcon) {
    }

    public void setHeaderVisibleAmount(float f) {
    }

    public void setIsChildInGroup(boolean z) {
    }

    public void setLegacy(boolean z) {
    }

    public void setRecentlyAudiblyAlerted(boolean z) {
    }

    public void setRemoteInputVisible(boolean z) {
    }

    public void updateContentDescription() {
    }

    public void setContentHeight(int i, int i2) {
    }

    public void updateExpandability(boolean z, View.OnClickListener onClickListener, boolean z2) {
    }
}
