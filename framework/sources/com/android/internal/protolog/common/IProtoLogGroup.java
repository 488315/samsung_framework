package com.android.internal.protolog.common;

/* loaded from: classes5.dex */
public interface IProtoLogGroup {
    int getId();

    String getTag();

    boolean isEnabled();

    boolean isLogToLogcat();

    boolean isLogToProto();

    String name();

    void setLogToLogcat(boolean z);

    void setLogToProto(boolean z);

    default boolean isLogToAny() {
        return isLogToLogcat() || isLogToProto();
    }
}
