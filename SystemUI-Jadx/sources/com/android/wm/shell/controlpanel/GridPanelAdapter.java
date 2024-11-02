package com.android.wm.shell.controlpanel;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.wm.shell.controlpanel.action.ControlPanelAction;
import com.android.wm.shell.controlpanel.utils.ControlPanelUtils;
import java.util.ArrayList;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class GridPanelAdapter extends BaseAdapter {
    public final ArrayList items = new ArrayList();
    public final Context mContext;
    public final boolean mIsEditPanel;
    public View.OnClickListener mOnClickListener;
    public View.OnDragListener mOnDragListener;
    public View.OnLongClickListener mOnLongClickListener;

    public GridPanelAdapter(Context context, boolean z) {
        this.mContext = context;
        this.mIsEditPanel = z;
    }

    @Override // android.widget.Adapter
    public final int getCount() {
        return this.items.size();
    }

    @Override // android.widget.Adapter
    public final Object getItem(int i) {
        return this.items.get(i);
    }

    @Override // android.widget.Adapter
    public final long getItemId(int i) {
        return i;
    }

    @Override // android.widget.Adapter
    public final View getView(int i, View view, ViewGroup viewGroup) {
        ControlPanelAction.Action action = (ControlPanelAction.Action) this.items.get(i);
        int value = action.getValue();
        int resourceIdByActionValue = ControlPanelAction.getResourceIdByActionValue(action.getValue());
        ListPopupWindow$$ExternalSyntheticOutline0.m("makeButton(), action : ", value, "GridPanelAdapter");
        RelativeLayout relativeLayout = (RelativeLayout) View.inflate(this.mContext, R.layout.assistantmenu_menubutton, null);
        boolean makeGridButton = ControlPanelUtils.makeGridButton(this.mContext, relativeLayout, value, resourceIdByActionValue, true, this.mIsEditPanel);
        View.OnClickListener onClickListener = this.mOnClickListener;
        if (onClickListener != null && makeGridButton) {
            relativeLayout.setOnClickListener(onClickListener);
            View.OnLongClickListener onLongClickListener = this.mOnLongClickListener;
            if (onLongClickListener != null && this.mOnDragListener != null) {
                relativeLayout.setOnLongClickListener(onLongClickListener);
                relativeLayout.setOnDragListener(this.mOnDragListener);
            }
        }
        return relativeLayout;
    }
}
