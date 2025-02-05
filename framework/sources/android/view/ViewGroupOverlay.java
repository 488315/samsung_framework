package android.view;

import android.content.Context;

/* loaded from: classes4.dex */
public class ViewGroupOverlay extends ViewOverlay {
    ViewGroupOverlay(Context context, View hostView) {
        super(context, hostView);
    }

    public void add(View view) {
        this.mOverlayViewGroup.add(view);
    }

    public void remove(View view) {
        this.mOverlayViewGroup.remove(view);
    }
}
