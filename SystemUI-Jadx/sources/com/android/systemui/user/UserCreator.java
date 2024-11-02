package com.android.systemui.user;

import android.content.Context;
import android.os.UserManager;
import java.util.concurrent.Executor;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class UserCreator {
    public final Executor bgExecutor;
    public final Context context;
    public final Executor mainExecutor;
    public final UserManager userManager;

    public UserCreator(Context context, UserManager userManager, Executor executor, Executor executor2) {
        this.context = context;
        this.userManager = userManager;
        this.mainExecutor = executor;
        this.bgExecutor = executor2;
    }
}
