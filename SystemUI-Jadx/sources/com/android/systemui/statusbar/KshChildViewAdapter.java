package com.android.systemui.statusbar;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import com.android.systemui.statusbar.model.KshData;
import com.android.systemui.statusbar.model.KshDataUtils;
import java.util.List;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class KshChildViewAdapter extends RecyclerView.Adapter {
    public List mData;
    public final Typeface mDefaultFont;
    public final LayoutInflater mInflater;
    public KshData mKshData;
    public final KshDataUtils mKshDataUtils;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView iconView;
        public final TextView keywordView;
        public final LinearLayout shortcutKeysContainer;

        public ViewHolder(KshChildViewAdapter kshChildViewAdapter, View view) {
            super(view);
            this.iconView = (ImageView) view.findViewById(R.id.keyboard_shortcuts_icon);
            this.keywordView = (TextView) view.findViewById(R.id.keyboard_shortcuts_keyword);
            this.shortcutKeysContainer = (LinearLayout) view.findViewById(R.id.keyboard_shortcuts_item_container);
        }
    }

    public KshChildViewAdapter(Context context, LayoutInflater layoutInflater) {
        this.mInflater = layoutInflater;
        this.mKshDataUtils = new KshDataUtils(context);
        try {
            this.mDefaultFont = Typeface.createFromFile("/system/fonts/OneUISans-VF.ttf");
        } catch (Exception unused) {
            Log.e("KshChildViewAdapter", "/system/fonts/OneUISans-VF.ttf is not enabled");
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.mData.size();
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x00c5, code lost:
    
        if (r15 == null) goto L40;
     */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onBindViewHolder(androidx.recyclerview.widget.RecyclerView.ViewHolder r14, int r15) {
        /*
            Method dump skipped, instructions count: 290
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.KshChildViewAdapter.onBindViewHolder(androidx.recyclerview.widget.RecyclerView$ViewHolder, int):void");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
        return new ViewHolder(this, this.mInflater.inflate(R.layout.samsung_keyboard_shortcut_item_view, (ViewGroup) recyclerView, false));
    }
}
