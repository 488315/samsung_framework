package com.android.systemui.statusbar.notification.row.wrapper;

import android.R;
import android.content.Context;
import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.widget.MessagingGroup;
import com.android.internal.widget.MessagingImageMessage;
import com.android.internal.widget.MessagingLayout;
import com.android.internal.widget.MessagingLinearLayout;
import com.android.systemui.statusbar.TransformableView;
import com.android.systemui.statusbar.ViewTransformationHelper;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.TransformState;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.HybridNotificationView;
import java.util.Iterator;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class NotificationMessagingTemplateViewWrapper extends NotificationTemplateViewWrapper {
    public ViewGroup mImageMessageContainer;
    public final MessagingLayout mMessagingLayout;
    public MessagingLinearLayout mMessagingLinearLayout;
    public final int mMinHeightWithActions;
    public final View mTitle;
    public final View mTitleInHeader;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* renamed from: com.android.systemui.statusbar.notification.row.wrapper.NotificationMessagingTemplateViewWrapper$1, reason: invalid class name */
    /* loaded from: classes2.dex */
    public final class AnonymousClass1 extends ViewTransformationHelper.CustomTransformation {
        @Override // com.android.systemui.statusbar.ViewTransformationHelper.CustomTransformation
        public final boolean transformFrom(TransformState transformState, TransformableView transformableView, float f) {
            return transformTo(transformState, transformableView, f);
        }

        @Override // com.android.systemui.statusbar.ViewTransformationHelper.CustomTransformation
        public final boolean transformTo(TransformState transformState, TransformableView transformableView, float f) {
            if (transformableView instanceof HybridNotificationView) {
                return false;
            }
            transformState.ensureVisible();
            return true;
        }
    }

    public NotificationMessagingTemplateViewWrapper(Context context, View view, ExpandableNotificationRow expandableNotificationRow) {
        super(context, view, expandableNotificationRow);
        this.mTitle = this.mView.findViewById(R.id.title);
        this.mTitleInHeader = this.mView.findViewById(R.id.linear);
        this.mMessagingLayout = (MessagingLayout) view;
        this.mMinHeightWithActions = NotificationUtils.getFontScaledHeight(com.android.systemui.R.dimen.notification_messaging_actions_min_height, context);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final int getMinLayoutHeight() {
        View view = this.mActionsContainer;
        if (view != null && view.getVisibility() != 8) {
            return this.mMinHeightWithActions;
        }
        return 0;
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationTemplateViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final void onContentUpdated(ExpandableNotificationRow expandableNotificationRow) {
        MessagingLayout messagingLayout = this.mMessagingLayout;
        this.mMessagingLinearLayout = messagingLayout.getMessagingLinearLayout();
        this.mImageMessageContainer = messagingLayout.getImageMessageContainer();
        super.onContentUpdated(expandableNotificationRow);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final void setAnimationsRunning(boolean z) {
        MessagingLayout messagingLayout = this.mMessagingLayout;
        if (messagingLayout == null) {
            return;
        }
        Iterator it = messagingLayout.getMessagingGroups().iterator();
        while (it.hasNext()) {
            MessagingGroup messagingGroup = (MessagingGroup) it.next();
            for (int i = 0; i < messagingGroup.getMessageContainer().getChildCount(); i++) {
                MessagingImageMessage childAt = messagingGroup.getMessageContainer().getChildAt(i);
                if (childAt instanceof MessagingImageMessage) {
                    Drawable drawable = childAt.getDrawable();
                    if (drawable instanceof AnimatedImageDrawable) {
                        AnimatedImageDrawable animatedImageDrawable = (AnimatedImageDrawable) drawable;
                        if (z) {
                            animatedImageDrawable.start();
                        } else {
                            animatedImageDrawable.stop();
                        }
                    }
                }
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper
    public final void setRemoteInputVisible(boolean z) {
        this.mMessagingLayout.showHistoricMessages(z);
    }

    @Override // com.android.systemui.statusbar.notification.row.wrapper.NotificationTemplateViewWrapper, com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper
    public final void updateTransformedTypes() {
        View view;
        super.updateTransformedTypes();
        View view2 = this.mMessagingLinearLayout;
        ViewTransformationHelper viewTransformationHelper = this.mTransformationHelper;
        if (view2 != null) {
            viewTransformationHelper.addTransformedView(view2);
        }
        if (this.mTitle == null && (view = this.mTitleInHeader) != null) {
            viewTransformationHelper.addTransformedView(view, 1);
        }
        ViewGroup viewGroup = this.mImageMessageContainer;
        if (viewGroup != null) {
            viewTransformationHelper.setCustomTransformation(new AnonymousClass1(), viewGroup.getId());
        }
    }
}
