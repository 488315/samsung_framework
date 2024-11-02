package com.android.systemui.temporarydisplay;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import com.android.systemui.util.ViewController;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class TouchableRegionViewController extends ViewController {
    public final TouchableRegionViewController$internalInsetsListener$1 internalInsetsListener;
    public final Rect tempRect;

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.temporarydisplay.TouchableRegionViewController$internalInsetsListener$1] */
    public TouchableRegionViewController(View view, final Function2 function2) {
        super(view);
        this.tempRect = new Rect();
        this.internalInsetsListener = new ViewTreeObserver.OnComputeInternalInsetsListener() { // from class: com.android.systemui.temporarydisplay.TouchableRegionViewController$internalInsetsListener$1
            public final void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
                internalInsetsInfo.setTouchableInsets(3);
                TouchableRegionViewController.this.tempRect.setEmpty();
                Function2 function22 = function2;
                TouchableRegionViewController touchableRegionViewController = TouchableRegionViewController.this;
                function22.invoke(touchableRegionViewController.mView, touchableRegionViewController.tempRect);
                internalInsetsInfo.touchableRegion.set(TouchableRegionViewController.this.tempRect);
            }
        };
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        this.mView.getViewTreeObserver().addOnComputeInternalInsetsListener(this.internalInsetsListener);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        this.mView.getViewTreeObserver().removeOnComputeInternalInsetsListener(this.internalInsetsListener);
    }
}
