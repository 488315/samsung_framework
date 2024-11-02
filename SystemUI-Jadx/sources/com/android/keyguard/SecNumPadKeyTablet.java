package com.android.keyguard;

import android.content.Context;
import android.util.AttributeSet;
import com.android.systemui.R;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public class SecNumPadKeyTablet extends SecNumPadKey {
    public SecNumPadKeyTablet(Context context) {
        this(context, null);
    }

    @Override // com.android.keyguard.SecNumPadKey
    public final void updateDigitTextSize() {
        this.mDigitText.setTextSize(0, getResources().getDimensionPixelSize(R.dimen.kg_pin_num_pad_font_size_tablet));
    }

    @Override // com.android.keyguard.SecNumPadKey
    public final void updateKlondikeTextSize() {
        this.mKlondikeText.setTextSize(0, getResources().getDimensionPixelSize(R.dimen.kg_pin_klondike_font_size_tablet));
    }

    public SecNumPadKeyTablet(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SecNumPadKeyTablet(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, R.layout.keyguard_sec_num_pad_key_tablet);
    }

    private SecNumPadKeyTablet(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i);
    }
}
