package androidx.customview.poolingcontainer;

import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.ViewGroupKt$children$1;
import androidx.core.view.ViewKt;
import java.util.Iterator;
import kotlin.sequences.SequenceBuilderIterator;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class PoolingContainer {
    private static final int PoolingContainerListenerHolderTag = R.id.pooling_container_listener_holder_tag;
    private static final int IsPoolingContainerTag = R.id.is_pooling_container_tag;

    public static final void addPoolingContainerListener(View view, PoolingContainerListener poolingContainerListener) {
        getPoolingContainerListenerHolder(view).addListener(poolingContainerListener);
    }

    public static final void callPoolingContainerOnRelease(View view) {
        Iterator it = ViewKt.getAllViews(view).iterator();
        while (true) {
            SequenceBuilderIterator sequenceBuilderIterator = (SequenceBuilderIterator) it;
            if (sequenceBuilderIterator.hasNext()) {
                getPoolingContainerListenerHolder((View) sequenceBuilderIterator.next()).onRelease();
            } else {
                return;
            }
        }
    }

    public static final void callPoolingContainerOnReleaseForChildren(ViewGroup viewGroup) {
        Iterator it = new ViewGroupKt$children$1(viewGroup).iterator();
        while (it.hasNext()) {
            getPoolingContainerListenerHolder((View) it.next()).onRelease();
        }
    }

    private static final PoolingContainerListenerHolder getPoolingContainerListenerHolder(View view) {
        int i = PoolingContainerListenerHolderTag;
        PoolingContainerListenerHolder poolingContainerListenerHolder = (PoolingContainerListenerHolder) view.getTag(i);
        if (poolingContainerListenerHolder == null) {
            PoolingContainerListenerHolder poolingContainerListenerHolder2 = new PoolingContainerListenerHolder();
            view.setTag(i, poolingContainerListenerHolder2);
            return poolingContainerListenerHolder2;
        }
        return poolingContainerListenerHolder;
    }

    public static final boolean isPoolingContainer(View view) {
        Boolean bool;
        Object tag = view.getTag(IsPoolingContainerTag);
        if (tag instanceof Boolean) {
            bool = (Boolean) tag;
        } else {
            bool = null;
        }
        if (bool != null) {
            return bool.booleanValue();
        }
        return false;
    }

    public static final boolean isWithinPoolingContainer(View view) {
        for (Object obj : ViewKt.getAncestors(view)) {
            if ((obj instanceof View) && isPoolingContainer((View) obj)) {
                return true;
            }
        }
        return false;
    }

    public static final void removePoolingContainerListener(View view, PoolingContainerListener poolingContainerListener) {
        getPoolingContainerListenerHolder(view).removeListener(poolingContainerListener);
    }

    public static final void setPoolingContainer(View view, boolean z) {
        view.setTag(IsPoolingContainerTag, Boolean.valueOf(z));
    }
}
