package com.sec.internal.ims.core.handler.secims.imsCommonStruc.Notify_;

/* loaded from: classes.dex */
public final class RrcEvent {
    public static final int REJECTED = 1;
    public static final int TIMER_EXPIRED = 2;
    public static final int UN = 0;
    public static final String[] names = {"UN", "REJECTED", "TIMER_EXPIRED"};

    private RrcEvent() {
    }

    public static String name(int i) {
        return names[i];
    }
}
