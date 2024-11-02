package com.google.protobuf;

import com.samsung.android.knox.custom.CustomDeviceManager;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class ExtensionRegistryLite {
    public static final ExtensionRegistryLite EMPTY_REGISTRY_LITE = new ExtensionRegistryLite(true);
    public static volatile ExtensionRegistryLite emptyRegistry;
    public final Map extensionsByNumber;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class ObjectIntPair {
        public final int number;
        public final Object object;

        public ObjectIntPair(Object obj, int i) {
            this.object = obj;
            this.number = i;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof ObjectIntPair)) {
                return false;
            }
            ObjectIntPair objectIntPair = (ObjectIntPair) obj;
            if (this.object != objectIntPair.object || this.number != objectIntPair.number) {
                return false;
            }
            return true;
        }

        public final int hashCode() {
            return (System.identityHashCode(this.object) * CustomDeviceManager.QUICK_PANEL_ALL) + this.number;
        }
    }

    public ExtensionRegistryLite() {
        this.extensionsByNumber = new HashMap();
    }

    public static ExtensionRegistryLite getEmptyRegistry() {
        ExtensionRegistryLite extensionRegistryLite = emptyRegistry;
        if (extensionRegistryLite == null) {
            synchronized (ExtensionRegistryLite.class) {
                extensionRegistryLite = emptyRegistry;
                if (extensionRegistryLite == null) {
                    Class cls = ExtensionRegistryFactory.EXTENSION_REGISTRY_CLASS;
                    ExtensionRegistryLite extensionRegistryLite2 = null;
                    if (cls != null) {
                        try {
                            extensionRegistryLite2 = (ExtensionRegistryLite) cls.getDeclaredMethod("getEmptyRegistry", new Class[0]).invoke(null, new Object[0]);
                        } catch (Exception unused) {
                        }
                    }
                    if (extensionRegistryLite2 == null) {
                        extensionRegistryLite2 = EMPTY_REGISTRY_LITE;
                    }
                    emptyRegistry = extensionRegistryLite2;
                    extensionRegistryLite = extensionRegistryLite2;
                }
            }
        }
        return extensionRegistryLite;
    }

    public ExtensionRegistryLite(ExtensionRegistryLite extensionRegistryLite) {
        if (extensionRegistryLite == EMPTY_REGISTRY_LITE) {
            this.extensionsByNumber = Collections.emptyMap();
        } else {
            this.extensionsByNumber = Collections.unmodifiableMap(extensionRegistryLite.extensionsByNumber);
        }
    }

    public ExtensionRegistryLite(boolean z) {
        this.extensionsByNumber = Collections.emptyMap();
    }
}
