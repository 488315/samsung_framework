package com.android.systemui.statusbar.notification;

import android.util.Pools;
import android.view.View;
import com.android.app.animation.Interpolators;
import com.android.internal.widget.IMessagingLayout;
import com.android.internal.widget.MessagingGroup;
import com.android.internal.widget.MessagingImageMessage;
import com.android.internal.widget.MessagingLinearLayout;
import com.android.internal.widget.MessagingPropertyAnimator;
import com.android.systemui.statusbar.notification.TransformState;
import java.util.ArrayList;
import java.util.HashMap;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class MessagingLayoutTransformState extends TransformState {
    public static final Pools.SimplePool sInstancePool = new Pools.SimplePool(40);
    public final HashMap mGroupMap = new HashMap();
    public IMessagingLayout mMessagingLayout;
    public float mRelativeTranslationOffset;

    public static ArrayList filterHiddenGroups(ArrayList arrayList) {
        ArrayList arrayList2 = new ArrayList(arrayList);
        int i = 0;
        while (i < arrayList2.size()) {
            if (isGone((MessagingGroup) arrayList2.get(i))) {
                arrayList2.remove(i);
                i--;
            }
            i++;
        }
        return arrayList2;
    }

    public static boolean isGone(View view) {
        if (view == null || view.getVisibility() == 8 || view.getParent() == null || view.getWidth() == 0) {
            return true;
        }
        MessagingLinearLayout.LayoutParams layoutParams = view.getLayoutParams();
        if ((layoutParams instanceof MessagingLinearLayout.LayoutParams) && layoutParams.hide) {
            return true;
        }
        return false;
    }

    public final void appear(View view, float f) {
        if (view != null && view.getVisibility() != 8) {
            TransformState createFrom = TransformState.createFrom(view, this.mTransformInfo);
            createFrom.appear(f, null);
            createFrom.recycle();
        }
    }

    public final void disappear(View view, float f) {
        if (view != null && view.getVisibility() != 8) {
            TransformState createFrom = TransformState.createFrom(view, this.mTransformInfo);
            createFrom.disappear(f, null);
            createFrom.recycle();
        }
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    public final void initFrom(View view, TransformState.TransformInfo transformInfo) {
        super.initFrom(view, transformInfo);
        MessagingLinearLayout messagingLinearLayout = this.mTransformedView;
        if (messagingLinearLayout instanceof MessagingLinearLayout) {
            this.mMessagingLayout = messagingLinearLayout.getMessagingLayout();
            this.mRelativeTranslationOffset = view.getContext().getResources().getDisplayMetrics().density * 8.0f;
        }
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    public final void prepareFadeIn() {
        resetTransformedView();
        setVisible(true, false);
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    public final void recycle() {
        super.recycle();
        this.mGroupMap.clear();
        sInstancePool.release(this);
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    public final void reset() {
        super.reset();
        this.mMessagingLayout = null;
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    public final void resetTransformedView() {
        super.resetTransformedView();
        ArrayList messagingGroups = this.mMessagingLayout.getMessagingGroups();
        for (int i = 0; i < messagingGroups.size(); i++) {
            MessagingGroup messagingGroup = (MessagingGroup) messagingGroups.get(i);
            if (!isGone(messagingGroup)) {
                MessagingLinearLayout messageContainer = messagingGroup.getMessageContainer();
                for (int i2 = 0; i2 < messageContainer.getChildCount(); i2++) {
                    View childAt = messageContainer.getChildAt(i2);
                    if (!isGone(childAt)) {
                        resetTransformedView(childAt);
                        TransformState.setClippingDeactivated(childAt, false);
                    }
                }
                resetTransformedView(messagingGroup.getAvatar());
                resetTransformedView(messagingGroup.getSenderView());
                MessagingImageMessage isolatedMessage = messagingGroup.getIsolatedMessage();
                if (isolatedMessage != null) {
                    resetTransformedView(isolatedMessage);
                }
                TransformState.setClippingDeactivated(messagingGroup.getAvatar(), false);
                TransformState.setClippingDeactivated(messagingGroup.getSenderView(), false);
                messagingGroup.setTranslationY(0.0f);
                messagingGroup.getMessageContainer().setTranslationY(0.0f);
                messagingGroup.getSenderView().setTranslationY(0.0f);
            }
            messagingGroup.setClippingDisabled(false);
            messagingGroup.updateClipRect();
        }
        this.mMessagingLayout.setMessagingClippingDisabled(false);
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    public final void setVisible(boolean z, boolean z2) {
        super.setVisible(z, z2);
        resetTransformedView();
        ArrayList messagingGroups = this.mMessagingLayout.getMessagingGroups();
        for (int i = 0; i < messagingGroups.size(); i++) {
            MessagingGroup messagingGroup = (MessagingGroup) messagingGroups.get(i);
            if (!isGone(messagingGroup)) {
                MessagingLinearLayout messageContainer = messagingGroup.getMessageContainer();
                for (int i2 = 0; i2 < messageContainer.getChildCount(); i2++) {
                    setVisible(messageContainer.getChildAt(i2), z, z2);
                }
                setVisible(messagingGroup.getAvatar(), z, z2);
                setVisible(messagingGroup.getSenderView(), z, z2);
                MessagingImageMessage isolatedMessage = messagingGroup.getIsolatedMessage();
                if (isolatedMessage != null) {
                    setVisible(isolatedMessage, z, z2);
                }
            }
        }
    }

    public final int transformView(float f, boolean z, View view, View view2, boolean z2, boolean z3) {
        boolean z4;
        TransformState createFrom = TransformState.createFrom(view, this.mTransformInfo);
        if (z3) {
            createFrom.mDefaultInterpolator = Interpolators.LINEAR;
        }
        int i = 0;
        if (z2 && !isGone(view2)) {
            z4 = true;
        } else {
            z4 = false;
        }
        createFrom.mSameAsAny = z4;
        if (z) {
            if (view2 != null) {
                TransformState createFrom2 = TransformState.createFrom(view2, this.mTransformInfo);
                if (!isGone(view2)) {
                    createFrom.transformViewTo(createFrom2, f);
                } else {
                    if (!isGone(view)) {
                        createFrom.disappear(f, null);
                    }
                    createFrom.transformViewTo(createFrom2, 16, null, f);
                }
                i = createFrom.getLaidOutLocationOnScreen()[1] - createFrom2.getLaidOutLocationOnScreen()[1];
                createFrom2.recycle();
            } else {
                createFrom.disappear(f, null);
            }
        } else if (view2 != null) {
            TransformState createFrom3 = TransformState.createFrom(view2, this.mTransformInfo);
            if (!isGone(view2)) {
                createFrom.transformViewFrom(createFrom3, f);
            } else {
                if (!isGone(view)) {
                    createFrom.appear(f, null);
                }
                createFrom.transformViewFrom(createFrom3, 16, null, f);
            }
            i = createFrom.getLaidOutLocationOnScreen()[1] - createFrom3.getLaidOutLocationOnScreen()[1];
            createFrom3.recycle();
        } else {
            createFrom.appear(f, null);
        }
        createFrom.recycle();
        return i;
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    public final void transformViewFrom(TransformState transformState, float f) {
        if (transformState instanceof MessagingLayoutTransformState) {
            transformViewInternal((MessagingLayoutTransformState) transformState, f, false);
        } else {
            super.transformViewFrom(transformState, f);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:102:0x00d1  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0106  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0192  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x01b0  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x01c2  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0194  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x01f7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void transformViewInternal(com.android.systemui.statusbar.notification.MessagingLayoutTransformState r31, float r32, boolean r33) {
        /*
            Method dump skipped, instructions count: 808
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.MessagingLayoutTransformState.transformViewInternal(com.android.systemui.statusbar.notification.MessagingLayoutTransformState, float, boolean):void");
    }

    @Override // com.android.systemui.statusbar.notification.TransformState
    public final boolean transformViewTo(TransformState transformState, float f) {
        if (transformState instanceof MessagingLayoutTransformState) {
            transformViewInternal((MessagingLayoutTransformState) transformState, f, true);
            return true;
        }
        return super.transformViewTo(transformState, f);
    }

    public final void setVisible(View view, boolean z, boolean z2) {
        if (isGone(view) || MessagingPropertyAnimator.isAnimatingAlpha(view)) {
            return;
        }
        TransformState createFrom = TransformState.createFrom(view, this.mTransformInfo);
        createFrom.setVisible(z, z2);
        createFrom.recycle();
    }

    public final void resetTransformedView(View view) {
        TransformState createFrom = TransformState.createFrom(view, this.mTransformInfo);
        createFrom.resetTransformedView();
        createFrom.recycle();
    }
}
