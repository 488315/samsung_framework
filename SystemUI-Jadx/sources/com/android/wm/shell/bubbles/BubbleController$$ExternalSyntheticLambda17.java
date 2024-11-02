package com.android.wm.shell.bubbles;

import android.util.Log;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.BubbleData;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class BubbleController$$ExternalSyntheticLambda17 implements Runnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ boolean f$2;

    public /* synthetic */ BubbleController$$ExternalSyntheticLambda17(BubbleController.IBubblesImpl iBubblesImpl, boolean z, String str) {
        this.f$0 = iBubblesImpl;
        this.f$2 = z;
        this.f$1 = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((BubbleController) this.f$0).expandIfChanged((BubbleData.Update) this.f$1, this.f$2);
                return;
            default:
                BubbleController.IBubblesImpl iBubblesImpl = (BubbleController.IBubblesImpl) this.f$0;
                boolean z = this.f$2;
                String str = (String) this.f$1;
                BubbleController.this.mBubblePositioner.mShowingInBubbleBar = z;
                BubbleData bubbleData = iBubblesImpl.mController.mBubbleData;
                Bubble anyBubbleWithkey = bubbleData.getAnyBubbleWithkey(str);
                if (anyBubbleWithkey != null) {
                    String str2 = anyBubbleWithkey.mKey;
                    if (bubbleData.hasBubbleInStackWithKey(str2)) {
                        Log.d("Bubbles", "setSelectedBubbleFromLauncher: " + anyBubbleWithkey);
                        bubbleData.mExpanded = true;
                        if (!anyBubbleWithkey.equals(bubbleData.mSelectedBubble)) {
                            boolean equals = "Overflow".equals(str2);
                            List list = bubbleData.mBubbles;
                            if (!((ArrayList) list).contains(anyBubbleWithkey) && !((ArrayList) bubbleData.mOverflowBubbles).contains(anyBubbleWithkey) && !equals) {
                                Log.e("Bubbles", "Cannot select bubble which doesn't exist! (" + anyBubbleWithkey + ") bubbles=" + list);
                            } else {
                                if (!equals) {
                                    ((BubbleData$$ExternalSyntheticLambda3) bubbleData.mTimeSource).getClass();
                                    anyBubbleWithkey.mLastAccessed = System.currentTimeMillis();
                                    anyBubbleWithkey.setSuppressNotification(true);
                                    anyBubbleWithkey.setShowDot(false);
                                }
                                bubbleData.mSelectedBubble = anyBubbleWithkey;
                            }
                        }
                        throw null;
                    }
                    if (!bubbleData.hasOverflowBubbleWithKey(str2)) {
                        MotionLayout$$ExternalSyntheticOutline0.m("didn't add bubble from launcher: ", str, "Bubbles");
                        return;
                    }
                    return;
                }
                return;
        }
    }

    public /* synthetic */ BubbleController$$ExternalSyntheticLambda17(BubbleController bubbleController, BubbleData.Update update, boolean z) {
        this.f$0 = bubbleController;
        this.f$1 = update;
        this.f$2 = z;
    }
}
