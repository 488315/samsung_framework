package com.android.systemui.media.controls.ui;

import com.android.systemui.monet.ColorScheme;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public /* synthetic */ class SecColorSchemeTransition$accentPrimary$1 extends FunctionReferenceImpl implements Function1 {
    public static final SecColorSchemeTransition$accentPrimary$1 INSTANCE = new SecColorSchemeTransition$accentPrimary$1();

    public SecColorSchemeTransition$accentPrimary$1() {
        super(1, MediaColorSchemesKt.class, "accentPrimaryFromScheme", "accentPrimaryFromScheme(Lcom/android/systemui/monet/ColorScheme;)I", 1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        return Integer.valueOf(((ColorScheme) obj).accent1.getS100());
    }
}