package android.view.accessibility;

import android.util.SparseArray;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/* loaded from: classes4.dex */
final class WeakSparseArray<E> {
    private final ReferenceQueue<E> mRefQueue = new ReferenceQueue<>();
    private final SparseArray<WeakReferenceWithId<E>> mSparseArray = new SparseArray<>();

    WeakSparseArray() {
    }

    public void append(int key, E value) {
        removeUnreachableValues();
        this.mSparseArray.append(key, new WeakReferenceWithId<>(value, this.mRefQueue, key));
    }

    public void remove(int key) {
        removeUnreachableValues();
        this.mSparseArray.remove(key);
    }

    public E get(int i) {
        removeUnreachableValues();
        WeakReferenceWithId<E> weakReferenceWithId = this.mSparseArray.get(i);
        if (weakReferenceWithId != null) {
            return (E) weakReferenceWithId.get();
        }
        return null;
    }

    private void removeUnreachableValues() {
        Reference ref = this.mRefQueue.poll();
        while (ref != null) {
            this.mSparseArray.remove(((WeakReferenceWithId) ref).mId);
            ref = this.mRefQueue.poll();
        }
    }

    private static class WeakReferenceWithId<E> extends WeakReference<E> {
        final int mId;

        WeakReferenceWithId(E referent, ReferenceQueue<? super E> q, int id) {
            super(referent, q);
            this.mId = id;
        }
    }
}
