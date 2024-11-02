package com.android.systemui.settings;

import android.hardware.display.DisplayManager;
import android.os.Handler;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.settings.DisplayTrackerImpl;
import com.android.systemui.util.Assert;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Predicate;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public final class DisplayTrackerImpl implements DisplayTracker {
    public final Handler backgroundHandler;
    public final DisplayManager displayManager;
    public final List displayCallbacks = new ArrayList();
    public final List brightnessCallbacks = new ArrayList();
    public final DisplayTrackerImpl$displayChangedListener$1 displayChangedListener = new DisplayManager.DisplayListener() { // from class: com.android.systemui.settings.DisplayTrackerImpl$displayChangedListener$1
        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayAdded(final int i) {
            List<DisplayTrackerImpl.DisplayTrackerDataItem> list;
            DisplayTrackerImpl displayTrackerImpl = DisplayTrackerImpl.this;
            synchronized (displayTrackerImpl.displayCallbacks) {
                list = CollectionsKt___CollectionsKt.toList(displayTrackerImpl.displayCallbacks);
            }
            DisplayTrackerImpl.this.getClass();
            Assert.isNotMainThread();
            for (final DisplayTrackerImpl.DisplayTrackerDataItem displayTrackerDataItem : list) {
                if (displayTrackerDataItem.callback.get() != null) {
                    displayTrackerDataItem.executor.execute(new Runnable() { // from class: com.android.systemui.settings.DisplayTrackerImpl$onDisplayAdded$$inlined$notifySubscribers$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            DisplayTracker.Callback callback = (DisplayTracker.Callback) DisplayTrackerImpl.DisplayTrackerDataItem.this.callback.get();
                            if (callback != null) {
                                callback.onDisplayAdded(i);
                            }
                        }
                    });
                }
            }
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayChanged(int i) {
            List list;
            DisplayTrackerImpl displayTrackerImpl = DisplayTrackerImpl.this;
            synchronized (displayTrackerImpl.displayCallbacks) {
                list = CollectionsKt___CollectionsKt.toList(displayTrackerImpl.displayCallbacks);
            }
            DisplayTrackerImpl.access$onDisplayChanged(DisplayTrackerImpl.this, i, list);
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayRemoved(final int i) {
            List<DisplayTrackerImpl.DisplayTrackerDataItem> list;
            DisplayTrackerImpl displayTrackerImpl = DisplayTrackerImpl.this;
            synchronized (displayTrackerImpl.displayCallbacks) {
                list = CollectionsKt___CollectionsKt.toList(displayTrackerImpl.displayCallbacks);
            }
            DisplayTrackerImpl.this.getClass();
            Assert.isNotMainThread();
            for (final DisplayTrackerImpl.DisplayTrackerDataItem displayTrackerDataItem : list) {
                if (displayTrackerDataItem.callback.get() != null) {
                    displayTrackerDataItem.executor.execute(new Runnable() { // from class: com.android.systemui.settings.DisplayTrackerImpl$onDisplayRemoved$$inlined$notifySubscribers$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            DisplayTracker.Callback callback = (DisplayTracker.Callback) DisplayTrackerImpl.DisplayTrackerDataItem.this.callback.get();
                            if (callback != null) {
                                callback.onDisplayRemoved(i);
                            }
                        }
                    });
                }
            }
        }
    };
    public final DisplayTrackerImpl$displayBrightnessChangedListener$1 displayBrightnessChangedListener = new DisplayManager.DisplayListener() { // from class: com.android.systemui.settings.DisplayTrackerImpl$displayBrightnessChangedListener$1
        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayChanged(int i) {
            List list;
            DisplayTrackerImpl displayTrackerImpl = DisplayTrackerImpl.this;
            synchronized (displayTrackerImpl.brightnessCallbacks) {
                list = CollectionsKt___CollectionsKt.toList(displayTrackerImpl.brightnessCallbacks);
            }
            DisplayTrackerImpl.access$onDisplayChanged(DisplayTrackerImpl.this, i, list);
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayAdded(int i) {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayRemoved(int i) {
        }
    };

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes2.dex */
    public final class DisplayTrackerDataItem {
        public final WeakReference callback;
        public final Executor executor;

        public DisplayTrackerDataItem(WeakReference<DisplayTracker.Callback> weakReference, Executor executor) {
            this.callback = weakReference;
            this.executor = executor;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DisplayTrackerDataItem)) {
                return false;
            }
            DisplayTrackerDataItem displayTrackerDataItem = (DisplayTrackerDataItem) obj;
            if (Intrinsics.areEqual(this.callback, displayTrackerDataItem.callback) && Intrinsics.areEqual(this.executor, displayTrackerDataItem.executor)) {
                return true;
            }
            return false;
        }

        public final int hashCode() {
            return this.executor.hashCode() + (this.callback.hashCode() * 31);
        }

        public final String toString() {
            return "DisplayTrackerDataItem(callback=" + this.callback + ", executor=" + this.executor + ")";
        }
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.settings.DisplayTrackerImpl$displayChangedListener$1] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.settings.DisplayTrackerImpl$displayBrightnessChangedListener$1] */
    public DisplayTrackerImpl(DisplayManager displayManager, Handler handler) {
        this.displayManager = displayManager;
        this.backgroundHandler = handler;
    }

    public static final void access$onDisplayChanged(DisplayTrackerImpl displayTrackerImpl, final int i, List list) {
        displayTrackerImpl.getClass();
        Assert.isNotMainThread();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            final DisplayTrackerDataItem displayTrackerDataItem = (DisplayTrackerDataItem) it.next();
            if (displayTrackerDataItem.callback.get() != null) {
                displayTrackerDataItem.executor.execute(new Runnable() { // from class: com.android.systemui.settings.DisplayTrackerImpl$onDisplayChanged$$inlined$notifySubscribers$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        DisplayTracker.Callback callback = (DisplayTracker.Callback) DisplayTrackerImpl.DisplayTrackerDataItem.this.callback.get();
                        if (callback != null) {
                            callback.onDisplayChanged(i);
                        }
                    }
                });
            }
        }
    }

    public final void addBrightnessChangeCallback(DisplayTracker.Callback callback, Executor executor) {
        synchronized (this.brightnessCallbacks) {
            if (((ArrayList) this.brightnessCallbacks).isEmpty()) {
                this.displayManager.registerDisplayListener(this.displayBrightnessChangedListener, this.backgroundHandler, 8L);
            }
            ((ArrayList) this.brightnessCallbacks).add(new DisplayTrackerDataItem(new WeakReference(callback), executor));
        }
    }

    public final void addDisplayChangeCallback(DisplayTracker.Callback callback, Executor executor) {
        synchronized (this.displayCallbacks) {
            if (((ArrayList) this.displayCallbacks).isEmpty()) {
                this.displayManager.registerDisplayListener(this.displayChangedListener, this.backgroundHandler);
            }
            ((ArrayList) this.displayCallbacks).add(new DisplayTrackerDataItem(new WeakReference(callback), executor));
        }
    }

    public final void removeCallback(final DisplayTracker.Callback callback) {
        synchronized (this.displayCallbacks) {
            if (((ArrayList) this.displayCallbacks).removeIf(new Predicate() { // from class: com.android.systemui.settings.DisplayTrackerImpl$removeCallback$1$changed$1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    DisplayTracker.Callback callback2 = DisplayTracker.Callback.this;
                    DisplayTracker.Callback callback3 = (DisplayTracker.Callback) ((DisplayTrackerImpl.DisplayTrackerDataItem) obj).callback.get();
                    if (callback3 != null) {
                        return callback3.equals(callback2);
                    }
                    return true;
                }
            }) && ((ArrayList) this.displayCallbacks).isEmpty()) {
                this.displayManager.unregisterDisplayListener(this.displayChangedListener);
            }
            Unit unit = Unit.INSTANCE;
        }
        synchronized (this.brightnessCallbacks) {
            if (((ArrayList) this.brightnessCallbacks).removeIf(new Predicate() { // from class: com.android.systemui.settings.DisplayTrackerImpl$removeCallback$2$changed$1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    DisplayTracker.Callback callback2 = DisplayTracker.Callback.this;
                    DisplayTracker.Callback callback3 = (DisplayTracker.Callback) ((DisplayTrackerImpl.DisplayTrackerDataItem) obj).callback.get();
                    if (callback3 != null) {
                        return callback3.equals(callback2);
                    }
                    return true;
                }
            }) && ((ArrayList) this.brightnessCallbacks).isEmpty()) {
                this.displayManager.unregisterDisplayListener(this.displayBrightnessChangedListener);
            }
        }
    }

    public static /* synthetic */ void getDisplayBrightnessChangedListener$annotations() {
    }

    public static /* synthetic */ void getDisplayChangedListener$annotations() {
    }
}
