package com.samsung.context.sdk.samsunganalytics.internal.connection;

/* loaded from: classes.dex */
public enum Directory {
    DEVICE_CONTROLLER_DIR("/v1/quotas"),
    DATA_DELETE("/app/delete"),
    DLS_DIR(""),
    DLS_DIR_BAT("");

    String directory;

    Directory(String str) {
        this.directory = str;
    }

    public final void setDirectory(String str) {
        this.directory = str;
    }
}
