package com.sec.internal.interfaces.ims.cmstore;

/* loaded from: classes.dex */
public interface IUIEventCallback {
    public static final String NON_POP_UP = "non_pop_up";
    public static final String POP_UP = "pop_up";

    void notifyAppUIScreen(int i, String str, int i2);

    void showInitsyncIndicator(boolean z);
}
