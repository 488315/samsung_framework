package android.view;

import android.os.Looper;
import android.os.MessageQueue;
import android.util.LongSparseArray;
import android.util.Pools;
import dalvik.system.CloseGuard;
import java.lang.ref.WeakReference;

/* loaded from: classes4.dex */
public final class InputQueue {
    private final LongSparseArray<ActiveInputEvent> mActiveEventArray = new LongSparseArray<>(20);
    private final Pools.Pool<ActiveInputEvent> mActiveInputEventPool = new Pools.SimplePool(20);
    private final CloseGuard mCloseGuard = CloseGuard.get();
    private long mPtr = nativeInit(new WeakReference(this), Looper.myQueue());

    public interface Callback {
        void onInputQueueCreated(InputQueue inputQueue);

        void onInputQueueDestroyed(InputQueue inputQueue);
    }

    public interface FinishedInputEventCallback {
        void onFinishedInputEvent(Object obj, boolean z);
    }

    private static native void nativeDispose(long j);

    private static native long nativeInit(WeakReference<InputQueue> weakReference, MessageQueue messageQueue);

    private static native long nativeSendKeyEvent(long j, KeyEvent keyEvent, boolean z);

    private static native long nativeSendMotionEvent(long j, MotionEvent motionEvent);

    public InputQueue() {
        this.mCloseGuard.open("InputQueue.dispose");
    }

    protected void finalize() throws Throwable {
        try {
            dispose(true);
        } finally {
            super.finalize();
        }
    }

    public void dispose() {
        dispose(false);
    }

    public void dispose(boolean finalized) {
        if (this.mCloseGuard != null) {
            if (finalized) {
                this.mCloseGuard.warnIfOpen();
            }
            this.mCloseGuard.close();
        }
        if (this.mPtr != 0) {
            nativeDispose(this.mPtr);
            this.mPtr = 0L;
        }
    }

    public long getNativePtr() {
        return this.mPtr;
    }

    public void sendInputEvent(InputEvent e, Object token, boolean predispatch, FinishedInputEventCallback callback) {
        long id;
        ActiveInputEvent event = obtainActiveInputEvent(token, callback);
        if (e instanceof KeyEvent) {
            id = nativeSendKeyEvent(this.mPtr, (KeyEvent) e, predispatch);
        } else if (((MotionEvent) e).mNeedWindowOffset) {
            MotionEvent original = (MotionEvent) e;
            float offsetX = original.getRawX() - original.getX();
            float offsetY = original.getRawY() - original.getY();
            MotionEvent adjustedEvent = MotionEvent.obtain(original, -offsetX, -offsetY);
            long id2 = nativeSendMotionEvent(this.mPtr, adjustedEvent);
            adjustedEvent.recycle();
            id = id2;
        } else {
            id = nativeSendMotionEvent(this.mPtr, (MotionEvent) e);
        }
        this.mActiveEventArray.put(id, event);
    }

    private void finishInputEvent(long id, boolean handled) {
        int index = this.mActiveEventArray.indexOfKey(id);
        if (index >= 0) {
            ActiveInputEvent e = this.mActiveEventArray.valueAt(index);
            this.mActiveEventArray.removeAt(index);
            e.mCallback.onFinishedInputEvent(e.mToken, handled);
            recycleActiveInputEvent(e);
        }
    }

    private ActiveInputEvent obtainActiveInputEvent(Object token, FinishedInputEventCallback callback) {
        ActiveInputEvent e = this.mActiveInputEventPool.acquire();
        if (e == null) {
            e = new ActiveInputEvent();
        }
        e.mToken = token;
        e.mCallback = callback;
        return e;
    }

    private void recycleActiveInputEvent(ActiveInputEvent e) {
        e.recycle();
        this.mActiveInputEventPool.release(e);
    }

    private final class ActiveInputEvent {
        public FinishedInputEventCallback mCallback;
        public Object mToken;

        private ActiveInputEvent() {
        }

        public void recycle() {
            this.mToken = null;
            this.mCallback = null;
        }
    }
}
