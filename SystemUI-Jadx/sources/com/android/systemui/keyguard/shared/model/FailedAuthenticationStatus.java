package com.android.systemui.keyguard.shared.model;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class FailedAuthenticationStatus extends AuthenticationStatus {
    public static final FailedAuthenticationStatus INSTANCE = new FailedAuthenticationStatus();

    private FailedAuthenticationStatus() {
        super(null);
    }
}