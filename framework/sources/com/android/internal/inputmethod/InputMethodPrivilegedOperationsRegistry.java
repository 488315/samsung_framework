package com.android.internal.inputmethod;

import android.os.IBinder;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/* loaded from: classes5.dex */
public final class InputMethodPrivilegedOperationsRegistry {
    private static final Object sLock = new Object();
    private static InputMethodPrivilegedOperations sNop;
    private static WeakHashMap<IBinder, WeakReference<InputMethodPrivilegedOperations>> sRegistry;

    private InputMethodPrivilegedOperationsRegistry() {
    }

    private static InputMethodPrivilegedOperations getNopOps() {
        if (sNop == null) {
            sNop = new InputMethodPrivilegedOperations();
        }
        return sNop;
    }

    public static void put(IBinder token, InputMethodPrivilegedOperations ops) {
        synchronized (sLock) {
            if (sRegistry == null) {
                sRegistry = new WeakHashMap<>();
            }
            sRegistry.put(token, new WeakReference<>(ops));
        }
    }

    public static InputMethodPrivilegedOperations get(IBinder token) {
        synchronized (sLock) {
            if (sRegistry == null) {
                return getNopOps();
            }
            WeakReference<InputMethodPrivilegedOperations> wrapperRef = sRegistry.get(token);
            if (wrapperRef == null) {
                return getNopOps();
            }
            InputMethodPrivilegedOperations wrapper = wrapperRef.get();
            if (wrapper != null) {
                return wrapper;
            }
            return getNopOps();
        }
    }

    public static void remove(IBinder token) {
        synchronized (sLock) {
            if (sRegistry == null) {
                return;
            }
            sRegistry.remove(token);
            if (sRegistry.isEmpty()) {
                sRegistry = null;
            }
        }
    }
}
