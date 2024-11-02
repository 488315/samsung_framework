package com.android.systemui.statusbar.policy;

import android.content.Context;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.statusbar.policy.ExtensionController;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.leak.LeakDetector;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class ExtensionControllerImpl implements ExtensionController {
    public final Context mDefaultContext;
    public final LeakDetector mLeakDetector;
    public final PluginManager mPluginManager;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class ExtensionBuilder {
        public final ExtensionImpl mExtension;

        public /* synthetic */ ExtensionBuilder(ExtensionControllerImpl extensionControllerImpl, int i) {
            this(extensionControllerImpl);
        }

        public final ExtensionImpl build() {
            ExtensionImpl extensionImpl = this.mExtension;
            Collections.sort(extensionImpl.mProducers, Comparator.comparingInt(new ExtensionControllerImpl$ExtensionBuilder$$ExternalSyntheticLambda0()));
            ExtensionImpl.m1431$$Nest$mnotifyChanged(extensionImpl);
            return extensionImpl;
        }

        public final ExtensionBuilder withPlugin(Class cls) {
            String action = PluginManager.Helper.getAction(cls);
            ExtensionImpl extensionImpl = this.mExtension;
            extensionImpl.mProducers.add(new ExtensionImpl.PluginItem(action, cls, null));
            return this;
        }

        private ExtensionBuilder(ExtensionControllerImpl extensionControllerImpl) {
            this.mExtension = new ExtensionImpl(extensionControllerImpl, 0);
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class ExtensionImpl implements ExtensionController.Extension {
        public final ArrayList mCallbacks;
        public Object mItem;
        public Context mPluginContext;
        public final ArrayList mProducers;

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes2.dex */
        public final class Default implements Item {
            public final Supplier mSupplier;

            public Default(ExtensionImpl extensionImpl, Supplier<Object> supplier) {
                this.mSupplier = supplier;
            }

            @Override // com.android.systemui.statusbar.policy.ExtensionControllerImpl.Item
            public final Object get() {
                return this.mSupplier.get();
            }

            @Override // com.android.systemui.statusbar.policy.ExtensionControllerImpl.Item
            public final int sortOrder() {
                return 4;
            }
        }

        /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
        /* loaded from: classes2.dex */
        public final class PluginItem implements Item, PluginListener {
            public Plugin mItem;

            public PluginItem(String str, Class<Plugin> cls, ExtensionController.PluginConverter pluginConverter) {
                ExtensionControllerImpl.this.mPluginManager.addPluginListener(str, this, cls);
            }

            @Override // com.android.systemui.statusbar.policy.ExtensionControllerImpl.Item
            public final Object get() {
                return this.mItem;
            }

            @Override // com.android.systemui.plugins.PluginListener
            public final void onPluginConnected(Plugin plugin, Context context) {
                ExtensionImpl extensionImpl = ExtensionImpl.this;
                extensionImpl.mPluginContext = context;
                this.mItem = plugin;
                ExtensionImpl.m1431$$Nest$mnotifyChanged(extensionImpl);
            }

            @Override // com.android.systemui.plugins.PluginListener
            public final void onPluginDisconnected(Plugin plugin) {
                ExtensionImpl extensionImpl = ExtensionImpl.this;
                extensionImpl.mPluginContext = null;
                this.mItem = null;
                ExtensionImpl.m1431$$Nest$mnotifyChanged(extensionImpl);
            }

            @Override // com.android.systemui.statusbar.policy.ExtensionControllerImpl.Item
            public final int sortOrder() {
                return 0;
            }
        }

        /* renamed from: -$$Nest$mnotifyChanged, reason: not valid java name */
        public static void m1431$$Nest$mnotifyChanged(ExtensionImpl extensionImpl) {
            Object obj = extensionImpl.mItem;
            if (obj != null) {
                ExtensionControllerImpl.this.mLeakDetector.trackGarbage(obj);
            }
            extensionImpl.mItem = null;
            int i = 0;
            int i2 = 0;
            while (true) {
                ArrayList arrayList = extensionImpl.mProducers;
                if (i2 >= arrayList.size()) {
                    break;
                }
                Object obj2 = ((Item) arrayList.get(i2)).get();
                if (obj2 != null) {
                    extensionImpl.mItem = obj2;
                    break;
                }
                i2++;
            }
            while (true) {
                ArrayList arrayList2 = extensionImpl.mCallbacks;
                if (i < arrayList2.size()) {
                    ((Consumer) arrayList2.get(i)).accept(extensionImpl.mItem);
                    i++;
                } else {
                    return;
                }
            }
        }

        public /* synthetic */ ExtensionImpl(ExtensionControllerImpl extensionControllerImpl, int i) {
            this();
        }

        private ExtensionImpl() {
            this.mProducers = new ArrayList();
            this.mCallbacks = new ArrayList();
        }
    }

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public interface Item {
        Object get();

        int sortOrder();
    }

    public ExtensionControllerImpl(Context context, LeakDetector leakDetector, PluginManager pluginManager, TunerService tunerService, ConfigurationController configurationController) {
        this.mDefaultContext = context;
        this.mLeakDetector = leakDetector;
        this.mPluginManager = pluginManager;
    }
}
