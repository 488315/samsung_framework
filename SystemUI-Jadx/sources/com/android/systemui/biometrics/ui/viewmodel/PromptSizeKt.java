package com.android.systemui.biometrics.ui.viewmodel;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public abstract class PromptSizeKt {
    public static final boolean isNotSmall(PromptSize promptSize) {
        if (promptSize != null && promptSize != PromptSize.SMALL) {
            return true;
        }
        return false;
    }
}
