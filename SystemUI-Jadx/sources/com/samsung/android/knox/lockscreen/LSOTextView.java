package com.samsung.android.knox.lockscreen;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.widget.TextView;
import com.samsung.android.knox.lockscreen.LSOItemText;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public final class LSOTextView extends TextView {

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* renamed from: com.samsung.android.knox.lockscreen.LSOTextView$1, reason: invalid class name */
    /* loaded from: classes3.dex */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$samsung$android$knox$lockscreen$LSOItemText$LSOTextSize;

        static {
            int[] iArr = new int[LSOItemText.LSOTextSize.values().length];
            $SwitchMap$com$samsung$android$knox$lockscreen$LSOItemText$LSOTextSize = iArr;
            try {
                iArr[LSOItemText.LSOTextSize.TINY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$lockscreen$LSOItemText$LSOTextSize[LSOItemText.LSOTextSize.SMALL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$lockscreen$LSOItemText$LSOTextSize[LSOItemText.LSOTextSize.NORMAL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$lockscreen$LSOItemText$LSOTextSize[LSOItemText.LSOTextSize.LARGE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$lockscreen$LSOItemText$LSOTextSize[LSOItemText.LSOTextSize.HUGE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public LSOTextView(Context context, LSOItemText lSOItemText) {
        super(context);
        init(lSOItemText);
    }

    public final float getTextSize(LSOItemText lSOItemText) {
        if (!LSOUtils.isTablet()) {
            return lSOItemText.getTextSize().nativeVal;
        }
        float f = lSOItemText.getTextSize().nativeVal;
        int i = AnonymousClass1.$SwitchMap$com$samsung$android$knox$lockscreen$LSOItemText$LSOTextSize[lSOItemText.getTextSize().ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (i != 4) {
                        if (i == 5) {
                            return 3.6f;
                        }
                        return f;
                    }
                    return 2.6f;
                }
                return 2.0f;
            }
            return 1.93f;
        }
        return 1.85f;
    }

    public final void init(LSOItemText lSOItemText) {
        if (lSOItemText.isFieldUpdated(128)) {
            setText(lSOItemText.text);
        }
        if (lSOItemText.isFieldUpdated(256)) {
            setTextColor(lSOItemText.text_color);
        } else {
            setTextColor(EmergencyPhoneWidget.BG_COLOR);
        }
        setTextSize(0, getTextSize(lSOItemText) * getTextSize());
        if (lSOItemText.isFieldUpdated(1024)) {
            setTypeface(Typeface.DEFAULT, lSOItemText.text_style);
        }
        if (lSOItemText.isFieldUpdated(32)) {
            setGravity(lSOItemText.gravity);
        }
        if (lSOItemText.isFieldUpdated(64)) {
            LSOAttributeSet attrs = lSOItemText.getAttrs();
            if (attrs.containsKey(LSOAttrConst.ATTR_MAX_LINES)) {
                setMaxLines(attrs.getAsInteger(LSOAttrConst.ATTR_MAX_LINES).intValue());
                setEllipsize(TextUtils.TruncateAt.END);
            }
            if (attrs.containsKey(LSOAttrConst.ATTR_SINGLE_LINE)) {
                setSingleLine(attrs.getAsBoolean(LSOAttrConst.ATTR_SINGLE_LINE).booleanValue());
            }
        }
    }
}
