package com.sec.internal.ims.core.handler.secims.imsCommonStruc;

/* loaded from: classes.dex */
public final class Result {
    public static final int REQUEST_FAILURE = 1;
    public static final int REQUEST_SUCCESS = 0;
    public static final String[] names = {"REQUEST_SUCCESS", "REQUEST_FAILURE"};

    private Result() {
    }

    public static String name(int i) {
        return names[i];
    }
}
