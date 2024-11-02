package androidx.slice.widget;

import android.content.Context;
import android.util.TypedValue;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class ActionRow extends FrameLayout {
    public final LinearLayout mActionsGroup;
    public int mColor;
    public final int mIconPadding;
    public final int mSize;

    public ActionRow(Context context, boolean z) {
        super(context);
        this.mColor = EmergencyPhoneWidget.BG_COLOR;
        this.mSize = (int) TypedValue.applyDimension(1, 48.0f, context.getResources().getDisplayMetrics());
        this.mIconPadding = (int) TypedValue.applyDimension(1, 12.0f, context.getResources().getDisplayMetrics());
        LinearLayout linearLayout = new LinearLayout(context);
        this.mActionsGroup = linearLayout;
        linearLayout.setOrientation(0);
        linearLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -2));
        addView(linearLayout);
    }
}
