package com.android.systemui.navigationbar.plugin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.view.View;
import com.android.systemui.R;
import com.android.systemui.navigationbar.util.IconDrawableUtil;
import com.samsung.systemui.splugins.navigationbar.ButtonDispatcherProxyBase;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class TaskBarButtonDispatcherProxy implements ButtonDispatcherProxyBase {
    public final Context context;
    public final Bundle pluginBundle;
    public final TaskbarButtonDispatcher pinButton = new TaskbarButtonDispatcher(this, 0, null, 2, null);
    public final int pinID = R.id.navbar_pin;
    public final List extraKeyIDList = CollectionsKt__CollectionsKt.listOf(Integer.valueOf(R.id.nav_custom_key_1), Integer.valueOf(R.id.nav_custom_key_2), Integer.valueOf(R.id.nav_custom_key_3), Integer.valueOf(R.id.nav_custom_key_4), Integer.valueOf(R.id.nav_custom_key_5));

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class TaskbarButtonDispatcher {
        public int id;
        public final View view;

        public TaskbarButtonDispatcher(TaskBarButtonDispatcherProxy taskBarButtonDispatcherProxy, int i, View view) {
            this.id = i;
            this.view = view;
        }

        public TaskbarButtonDispatcher(TaskBarButtonDispatcherProxy taskBarButtonDispatcherProxy, int i, View view, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this(taskBarButtonDispatcherProxy, i, (i2 & 2) != 0 ? new View(taskBarButtonDispatcherProxy.context) : view);
        }
    }

    public TaskBarButtonDispatcherProxy(Context context, Bundle bundle) {
        this.context = context;
        this.pluginBundle = bundle;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ButtonDispatcherProxyBase
    public final View getButtonView(int i) {
        return null;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ButtonDispatcherProxyBase
    public final void setButtonImage(int i, Drawable drawable, Drawable drawable2) {
        Bitmap bitmap = IconDrawableUtil.getBitmap(drawable);
        Bitmap bitmap2 = IconDrawableUtil.getBitmap(drawable2);
        int i2 = this.pinID;
        Bundle bundle = this.pluginBundle;
        if (i == i2) {
            this.pinButton.id = i;
            bundle.putParcelable("pin_LIGHT", bitmap);
            bundle.putParcelable("pin_DARK", bitmap2);
            return;
        }
        Iterator it = this.extraKeyIDList.iterator();
        int i3 = 1;
        while (it.hasNext()) {
            if (i == ((Number) it.next()).intValue()) {
                String m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("extra", i3);
                bundle.putParcelable(m + "_LIGHT", bitmap);
                bundle.putParcelable(m + "_DARK", bitmap2);
            }
            i3++;
        }
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ButtonDispatcherProxyBase
    public final void setButtonOnClickListener(int i, View.OnClickListener onClickListener) {
        TaskbarButtonDispatcher taskbarButtonDispatcher = this.pinButton;
        if (taskbarButtonDispatcher.id == i) {
            taskbarButtonDispatcher.view.setOnClickListener(onClickListener);
        }
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ButtonDispatcherProxyBase
    public final void addButton(int i) {
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ButtonDispatcherProxyBase
    public final void setButtonOnLongClickListener(int i, View.OnLongClickListener onLongClickListener) {
    }

    @Override // com.samsung.systemui.splugins.navigationbar.ButtonDispatcherProxyBase
    public final void setButtonVisibility(int i, int i2) {
    }
}
