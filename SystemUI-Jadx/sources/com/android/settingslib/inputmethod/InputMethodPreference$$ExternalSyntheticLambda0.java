package com.android.settingslib.inputmethod;

import android.content.DialogInterface;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final /* synthetic */ class InputMethodPreference$$ExternalSyntheticLambda0 implements DialogInterface.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ InputMethodPreference f$0;

    public /* synthetic */ InputMethodPreference$$ExternalSyntheticLambda0(InputMethodPreference inputMethodPreference, int i) {
        this.$r8$classId = i;
        this.f$0 = inputMethodPreference;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        switch (this.$r8$classId) {
            case 0:
                InputMethodPreference inputMethodPreference = this.f$0;
                int i2 = InputMethodPreference.$r8$clinit;
                inputMethodPreference.setChecked(true);
                throw null;
            case 1:
                InputMethodPreference inputMethodPreference2 = this.f$0;
                int i3 = InputMethodPreference.$r8$clinit;
                inputMethodPreference2.setChecked(false);
                throw null;
            case 2:
                InputMethodPreference inputMethodPreference3 = this.f$0;
                if (!inputMethodPreference3.mImi.getServiceInfo().directBootAware && !inputMethodPreference3.isTv()) {
                    inputMethodPreference3.showDirectBootWarnDialog();
                    return;
                } else {
                    inputMethodPreference3.setChecked(true);
                    throw null;
                }
            default:
                InputMethodPreference inputMethodPreference4 = this.f$0;
                int i4 = InputMethodPreference.$r8$clinit;
                inputMethodPreference4.setChecked(false);
                throw null;
        }
    }
}
