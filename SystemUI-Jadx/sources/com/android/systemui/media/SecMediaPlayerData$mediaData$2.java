package com.android.systemui.media;

import java.util.concurrent.ConcurrentHashMap;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public /* synthetic */ class SecMediaPlayerData$mediaData$2 extends FunctionReferenceImpl implements Function0 {
    public static final SecMediaPlayerData$mediaData$2 INSTANCE = new SecMediaPlayerData$mediaData$2();

    public SecMediaPlayerData$mediaData$2() {
        super(0, ConcurrentHashMap.class, "<init>", "<init>()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        return new ConcurrentHashMap();
    }
}
