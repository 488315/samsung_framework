package com.android.wm.shell.freeform;

import java.util.function.Consumer;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final /* synthetic */ class FreeformContainerItemController$$ExternalSyntheticLambda0 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        FreeformContainerItem freeformContainerItem = (FreeformContainerItem) obj;
        freeformContainerItem.mIconLoadCompleted = false;
        freeformContainerItem.mPublishCompleted = false;
    }
}