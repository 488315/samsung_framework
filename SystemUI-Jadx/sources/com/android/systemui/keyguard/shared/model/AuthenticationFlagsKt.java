package com.android.systemui.keyguard.shared.model;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public abstract class AuthenticationFlagsKt {
    public static final boolean access$containsFlag(int i, int i2) {
        if ((i & i2) != 0) {
            return true;
        }
        return false;
    }
}