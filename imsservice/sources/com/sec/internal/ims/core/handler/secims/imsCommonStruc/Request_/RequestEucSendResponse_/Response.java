package com.sec.internal.ims.core.handler.secims.imsCommonStruc.Request_.RequestEucSendResponse_;

/* loaded from: classes.dex */
public final class Response {
    public static final int ACCEPT = 0;
    public static final int DECLINE = 1;
    public static final String[] names = {"ACCEPT", "DECLINE"};

    private Response() {
    }

    public static String name(int i) {
        return names[i];
    }
}
