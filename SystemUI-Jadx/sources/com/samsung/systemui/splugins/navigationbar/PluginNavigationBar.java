package com.samsung.systemui.splugins.navigationbar;

import com.samsung.systemui.splugins.SPlugin;
import com.samsung.systemui.splugins.annotations.ProvidesInterface;
import java.io.PrintWriter;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
@ProvidesInterface(action = "com.samsung.systemui.navigationbar.PLUGIN", version = 9000)
/* loaded from: classes3.dex */
public interface PluginNavigationBar extends SPlugin {
    public static final String ACTION = "com.samsung.systemui.navigationbar.PLUGIN";
    public static final Companion Companion = Companion.$$INSTANCE;
    public static final int MAJOR_VERSION = 9;
    public static final int MINOR_VERSION = 0;
    public static final int VERSION = 9000;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes3.dex */
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final String ACTION = "com.samsung.systemui.navigationbar.PLUGIN";
        public static final int MAJOR_VERSION = 9;
        public static final int MINOR_VERSION = 0;
        public static final int VERSION = 9000;

        private Companion() {
        }
    }

    void connect();

    void disconnect();

    void dump(PrintWriter printWriter);

    @Override // com.samsung.systemui.splugins.SPlugin
    int getVersion();

    void onAttachedToWindow(ExtendableBar extendableBar);

    void onDetachedFromWindow(ExtendableBar extendableBar);

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes3.dex */
    public static final class DefaultImpls {
        public static int getVersion(PluginNavigationBar pluginNavigationBar) {
            return 9000;
        }

        public static void connect(PluginNavigationBar pluginNavigationBar) {
        }

        public static void dump(PluginNavigationBar pluginNavigationBar, PrintWriter printWriter) {
        }

        public static void onAttachedToWindow(PluginNavigationBar pluginNavigationBar, ExtendableBar extendableBar) {
        }

        public static void onDetachedFromWindow(PluginNavigationBar pluginNavigationBar, ExtendableBar extendableBar) {
        }
    }
}
