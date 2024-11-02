package com.android.systemui;

import android.os.Handler;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.systemui.BootAnimationFinishedCache;
import com.android.systemui.dump.DumpManager;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class BootAnimationFinishedCacheImpl implements BootAnimationFinishedCache, BootAnimationFinishedTrigger, Dumpable {
    public final AtomicBoolean bootAnimationFinished;
    public final List directListeners;
    public final List frontQueueListeners;
    public final List postlisteners;

    /* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
    /* loaded from: classes.dex */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public BootAnimationFinishedCacheImpl(DumpManager dumpManager) {
        DumpManager.registerDumpable$default(dumpManager, "BootAnimationFinishedCacheImpl", this);
        this.directListeners = new ArrayList();
        this.postlisteners = new ArrayList();
        this.frontQueueListeners = new ArrayList();
        this.bootAnimationFinished = new AtomicBoolean(false);
    }

    public final boolean addListener(BootAnimationFinishedCache.BootAnimationFinishedListener bootAnimationFinishedListener) {
        if (this.bootAnimationFinished.get()) {
            bootAnimationFinishedListener.onBootAnimationFinished();
        } else {
            synchronized (this) {
                if (this.bootAnimationFinished.get()) {
                    bootAnimationFinishedListener.onBootAnimationFinished();
                } else {
                    ((ArrayList) this.postlisteners).add(bootAnimationFinishedListener);
                    return false;
                }
            }
        }
        return true;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("BootAnimationFinishedCache state:");
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("  boot animation finished: ", this.bootAnimationFinished.get(), printWriter);
        if (!this.bootAnimationFinished.get()) {
            printWriter.println("  listeners:");
            synchronized (this) {
                Iterator it = ((ArrayList) this.directListeners).iterator();
                while (it.hasNext()) {
                    printWriter.println("    " + ((BootAnimationFinishedCache.BootAnimationFinishedListener) it.next()));
                }
                Iterator it2 = ((ArrayList) this.postlisteners).iterator();
                while (it2.hasNext()) {
                    printWriter.println("    " + ((BootAnimationFinishedCache.BootAnimationFinishedListener) it2.next()));
                }
                Iterator it3 = ((ArrayList) this.frontQueueListeners).iterator();
                while (it3.hasNext()) {
                    printWriter.println("    " + ((BootAnimationFinishedCache.BootAnimationFinishedListener) it3.next()));
                }
                Unit unit = Unit.INSTANCE;
            }
        }
    }

    public final void setBootAnimationFinished() {
        if (this.bootAnimationFinished.compareAndSet(false, true)) {
            synchronized (this) {
                Iterator it = ((ArrayList) this.directListeners).iterator();
                while (it.hasNext()) {
                    ((BootAnimationFinishedCache.BootAnimationFinishedListener) it.next()).onBootAnimationFinished();
                }
                Iterator it2 = ((ArrayList) this.postlisteners).iterator();
                while (it2.hasNext()) {
                    final BootAnimationFinishedCache.BootAnimationFinishedListener bootAnimationFinishedListener = (BootAnimationFinishedCache.BootAnimationFinishedListener) it2.next();
                    new Handler().post(new Runnable() { // from class: com.android.systemui.BootAnimationFinishedCacheImpl$setBootAnimationFinished$1$2$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            BootAnimationFinishedCache.BootAnimationFinishedListener.this.onBootAnimationFinished();
                        }
                    });
                }
                Iterator it3 = ((ArrayList) this.frontQueueListeners).iterator();
                while (it3.hasNext()) {
                    final BootAnimationFinishedCache.BootAnimationFinishedListener bootAnimationFinishedListener2 = (BootAnimationFinishedCache.BootAnimationFinishedListener) it3.next();
                    new Handler().postAtFrontOfQueue(new Runnable() { // from class: com.android.systemui.BootAnimationFinishedCacheImpl$setBootAnimationFinished$1$3$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            BootAnimationFinishedCache.BootAnimationFinishedListener.this.onBootAnimationFinished();
                        }
                    });
                }
                ((ArrayList) this.directListeners).clear();
                ((ArrayList) this.postlisteners).clear();
                ((ArrayList) this.frontQueueListeners).clear();
                Unit unit = Unit.INSTANCE;
            }
        }
    }
}
