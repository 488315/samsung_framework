package com.android.server.accessibility;

import android.os.Binder;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.ArrayMap;
import android.util.Pools;
import android.util.Slog;
import android.view.InputEventConsistencyVerifier;
import android.view.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class KeyEventDispatcher implements Handler.Callback {
    public final Handler mHandlerToSendKeyEventsToInputFilter;
    public Handler mKeyEventTimeoutHandler;
    public final Object mLock;
    public final int mMessageTypeForSendKeyEvent;
    public final Pools.Pool mPendingEventPool = new Pools.SimplePool(10);
    public final Map mPendingEventsMap = new ArrayMap();
    public final PowerManager mPowerManager;
    public final InputEventConsistencyVerifier mSentEventsVerifier;

    /* loaded from: classes.dex */
    public interface KeyEventFilter {
        boolean onKeyEvent(KeyEvent keyEvent, int i);
    }

    /* loaded from: classes.dex */
    public final class PendingKeyEvent {
        public KeyEvent event;
        public boolean handled;
        public int policyFlags;
        public int referenceCount;

        public PendingKeyEvent() {
        }
    }

    public KeyEventDispatcher(Handler handler, int i, Object obj, PowerManager powerManager) {
        if (InputEventConsistencyVerifier.isInstrumentationEnabled()) {
            this.mSentEventsVerifier = new InputEventConsistencyVerifier(this, 0, KeyEventDispatcher.class.getSimpleName());
        } else {
            this.mSentEventsVerifier = null;
        }
        this.mHandlerToSendKeyEventsToInputFilter = handler;
        this.mMessageTypeForSendKeyEvent = i;
        this.mKeyEventTimeoutHandler = new Handler(handler.getLooper(), this);
        this.mLock = obj;
        this.mPowerManager = powerManager;
    }

    public boolean notifyKeyEventLocked(KeyEvent keyEvent, int i, List list) {
        KeyEvent obtain = KeyEvent.obtain(keyEvent);
        PendingKeyEvent pendingKeyEvent = null;
        for (int i2 = 0; i2 < list.size(); i2++) {
            KeyEventFilter keyEventFilter = (KeyEventFilter) list.get(i2);
            if (keyEventFilter.onKeyEvent(obtain, obtain.getSequenceNumber())) {
                if (pendingKeyEvent == null) {
                    pendingKeyEvent = obtainPendingEventLocked(obtain, i);
                }
                ArrayList arrayList = (ArrayList) this.mPendingEventsMap.get(keyEventFilter);
                if (arrayList == null) {
                    arrayList = new ArrayList();
                    this.mPendingEventsMap.put(keyEventFilter, arrayList);
                }
                arrayList.add(pendingKeyEvent);
                pendingKeyEvent.referenceCount++;
            }
        }
        if (pendingKeyEvent == null) {
            obtain.recycle();
            return false;
        }
        this.mKeyEventTimeoutHandler.sendMessageDelayed(this.mKeyEventTimeoutHandler.obtainMessage(1, pendingKeyEvent), 500L);
        return true;
    }

    public void setOnKeyEventResult(KeyEventFilter keyEventFilter, boolean z, int i) {
        synchronized (this.mLock) {
            PendingKeyEvent removeEventFromListLocked = removeEventFromListLocked((List) this.mPendingEventsMap.get(keyEventFilter), i);
            if (removeEventFromListLocked != null) {
                if (z && !removeEventFromListLocked.handled) {
                    removeEventFromListLocked.handled = z;
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        this.mPowerManager.userActivity(removeEventFromListLocked.event.getEventTime(), 3, 0);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    } catch (Throwable th) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
                removeReferenceToPendingEventLocked(removeEventFromListLocked);
            }
        }
    }

    public void flush(KeyEventFilter keyEventFilter) {
        synchronized (this.mLock) {
            List list = (List) this.mPendingEventsMap.get(keyEventFilter);
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    removeReferenceToPendingEventLocked((PendingKeyEvent) list.get(i));
                }
                this.mPendingEventsMap.remove(keyEventFilter);
            }
        }
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        if (message.what != 1) {
            Slog.w("KeyEventDispatcher", "Unknown message: " + message.what);
            return false;
        }
        PendingKeyEvent pendingKeyEvent = (PendingKeyEvent) message.obj;
        synchronized (this.mLock) {
            Iterator it = this.mPendingEventsMap.values().iterator();
            while (it.hasNext() && (!((ArrayList) it.next()).remove(pendingKeyEvent) || !removeReferenceToPendingEventLocked(pendingKeyEvent))) {
            }
        }
        return true;
    }

    public final PendingKeyEvent obtainPendingEventLocked(KeyEvent keyEvent, int i) {
        PendingKeyEvent pendingKeyEvent = (PendingKeyEvent) this.mPendingEventPool.acquire();
        if (pendingKeyEvent == null) {
            pendingKeyEvent = new PendingKeyEvent();
        }
        pendingKeyEvent.event = keyEvent;
        pendingKeyEvent.policyFlags = i;
        pendingKeyEvent.referenceCount = 0;
        pendingKeyEvent.handled = false;
        return pendingKeyEvent;
    }

    public static PendingKeyEvent removeEventFromListLocked(List list, int i) {
        for (int i2 = 0; i2 < list.size(); i2++) {
            PendingKeyEvent pendingKeyEvent = (PendingKeyEvent) list.get(i2);
            if (pendingKeyEvent.event.getSequenceNumber() == i) {
                list.remove(pendingKeyEvent);
                return pendingKeyEvent;
            }
        }
        return null;
    }

    public final boolean removeReferenceToPendingEventLocked(PendingKeyEvent pendingKeyEvent) {
        int i = pendingKeyEvent.referenceCount - 1;
        pendingKeyEvent.referenceCount = i;
        if (i > 0) {
            return false;
        }
        this.mKeyEventTimeoutHandler.removeMessages(1, pendingKeyEvent);
        if (!pendingKeyEvent.handled) {
            InputEventConsistencyVerifier inputEventConsistencyVerifier = this.mSentEventsVerifier;
            if (inputEventConsistencyVerifier != null) {
                inputEventConsistencyVerifier.onKeyEvent(pendingKeyEvent.event, 0);
            }
            this.mHandlerToSendKeyEventsToInputFilter.obtainMessage(this.mMessageTypeForSendKeyEvent, pendingKeyEvent.policyFlags | 1073741824, 0, pendingKeyEvent.event).sendToTarget();
        } else {
            pendingKeyEvent.event.recycle();
        }
        this.mPendingEventPool.release(pendingKeyEvent);
        return true;
    }
}
