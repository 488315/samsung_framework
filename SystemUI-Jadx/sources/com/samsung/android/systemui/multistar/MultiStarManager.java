package com.samsung.android.systemui.multistar;

import android.util.Singleton;
import com.samsung.systemui.splugins.multistar.PluginMultiStar;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public final class MultiStarManager {
    public static PluginMultiStar mPluginMultiStar = null;
    public static final AnonymousClass1 sInstance = new Singleton() { // from class: com.samsung.android.systemui.multistar.MultiStarManager.1
        public final Object create() {
            return new MultiStarManager(0);
        }
    };
    public static boolean sRecentKeyConsumed = false;
    public MultiStarSystemProxyImpl mMultiStarSystemFacade;

    public /* synthetic */ MultiStarManager(int i) {
        this();
    }

    private MultiStarManager() {
    }
}
