package com.android.systemui.qs.buttons;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.android.systemui.R;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class QSTooltipWindow {
    public static QSTooltipWindow sInstance;
    public final AnonymousClass1 handler = new Handler() { // from class: com.android.systemui.qs.buttons.QSTooltipWindow.1
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what == 100 && QSTooltipWindow.this.isTooltipShown()) {
                QSTooltipWindow.this.mTipWindow.dismiss();
            }
        }
    };
    public final View mContentView;
    public final Context mContext;
    public final PopupWindow mTipWindow;
    public int mToolTipString;
    public final TextView mTooltipText;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.qs.buttons.QSTooltipWindow$1] */
    public QSTooltipWindow(Context context) {
        this.mContext = context;
        PopupWindow popupWindow = new PopupWindow(context);
        this.mTipWindow = popupWindow;
        View inflate = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.sec_qs_tooltip_layout, (ViewGroup) null);
        this.mContentView = inflate;
        this.mTooltipText = (TextView) inflate.findViewById(R.id.tooltip_text);
        popupWindow.setHeight(-2);
        popupWindow.setWidth(-2);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(false);
        popupWindow.setElevation(context.getResources().getDimension(R.dimen.qs_layout_detail_popup_menu_elevation));
        popupWindow.setContentView(inflate);
        popupWindow.setSoftInputMode(3);
    }

    public static QSTooltipWindow getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new QSTooltipWindow(context);
        }
        return sInstance;
    }

    public final void hideToolTip() {
        if (isTooltipShown()) {
            this.mTipWindow.dismiss();
        }
    }

    public final boolean isTooltipShown() {
        PopupWindow popupWindow = this.mTipWindow;
        if (popupWindow != null && popupWindow.isShowing()) {
            return true;
        }
        return false;
    }

    public final void showToolTip(View view, int i) {
        this.mToolTipString = i;
        Context context = this.mContext;
        String string = context.getResources().getString(this.mToolTipString);
        TextView textView = this.mTooltipText;
        textView.setText(string);
        textView.setTextColor(context.getColor(R.color.tooltip_text_color));
        PopupWindow popupWindow = this.mTipWindow;
        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.sec_qs_tooltip_frame_background));
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        int i2 = iArr[0];
        Rect rect = new Rect(i2, iArr[1], view.getWidth() + i2, view.getHeight() + iArr[1]);
        View view2 = this.mContentView;
        view2.measure(-2, -2);
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.qs_multisim_preffered_slot_divider_padding_vertical);
        popupWindow.showAtLocation(view, 0, (rect.centerX() - (view2.getMeasuredWidth() / 2)) - dimensionPixelSize, (dimensionPixelSize / 2) + (rect.bottom - (rect.height() / 2)));
        AnonymousClass1 anonymousClass1 = this.handler;
        anonymousClass1.removeMessages(100);
        anonymousClass1.sendEmptyMessageDelayed(100, 4000L);
    }
}