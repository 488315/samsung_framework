package com.samsung.systemui.splugins;

import com.samsung.systemui.splugins.annotations.ProvidesInterface;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
@ProvidesInterface(version = 1)
/* loaded from: classes3.dex */
public class SPluginDependency {
    public static final int VERSION = 1;
    static DependencyProvider sProvider;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes3.dex */
    public static abstract class DependencyProvider {
        public abstract <T> T get(SPlugin sPlugin, Class<T> cls);
    }

    public static <T> T get(SPlugin sPlugin, Class<T> cls) {
        return (T) sProvider.get(sPlugin, cls);
    }
}