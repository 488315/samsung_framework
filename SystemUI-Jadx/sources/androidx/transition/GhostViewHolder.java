package androidx.transition;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.systemui.R;
import java.util.ArrayList;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class GhostViewHolder extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mAttached;
    public final ViewGroup mParent;

    public GhostViewHolder(ViewGroup viewGroup) {
        super(viewGroup.getContext());
        setClipChildren(false);
        this.mParent = viewGroup;
        viewGroup.setTag(R.id.ghost_view_holder, this);
        new ViewGroupOverlayApi18(viewGroup).mViewGroupOverlay.add(this);
        this.mAttached = true;
    }

    public static void getParents(ArrayList arrayList, View view) {
        Object parent = view.getParent();
        if (parent instanceof ViewGroup) {
            getParents(arrayList, (View) parent);
        }
        arrayList.add(view);
    }

    @Override // android.view.ViewGroup
    public final void onViewAdded(View view) {
        if (this.mAttached) {
            super.onViewAdded(view);
            return;
        }
        throw new IllegalStateException("This GhostViewHolder is detached!");
    }

    @Override // android.view.ViewGroup
    public final void onViewRemoved(View view) {
        super.onViewRemoved(view);
        if ((getChildCount() == 1 && getChildAt(0) == view) || getChildCount() == 0) {
            this.mParent.setTag(R.id.ghost_view_holder, null);
            new ViewGroupOverlayApi18(this.mParent).mViewGroupOverlay.remove(this);
            this.mAttached = false;
        }
    }
}
