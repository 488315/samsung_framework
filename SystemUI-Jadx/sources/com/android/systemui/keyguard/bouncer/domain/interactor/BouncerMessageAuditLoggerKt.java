package com.android.systemui.keyguard.bouncer.domain.interactor;

import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public abstract class BouncerMessageAuditLoggerKt {
    public static final String TAG;

    static {
        String simpleName = Reflection.getOrCreateKotlinClass(BouncerMessageAuditLogger.class).getSimpleName();
        Intrinsics.checkNotNull(simpleName);
        TAG = simpleName;
    }
}