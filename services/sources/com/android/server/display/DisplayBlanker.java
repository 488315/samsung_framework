package com.android.server.display;

/* loaded from: classes2.dex */
public interface DisplayBlanker {
    void requestDisplayState(int i, int i2, float f, float f2);

    void setDisplayStateLimitForEarlyWakeUp(int i, int i2);
}
