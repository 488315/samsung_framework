package kotlin.collections;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import java.util.Arrays;
import java.util.Iterator;
import java.util.RandomAccess;
import kotlin.collections.AbstractList;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public final class RingBuffer extends AbstractList implements RandomAccess {
    public final Object[] buffer;
    public final int capacity;
    public int size;
    public int startIndex;

    public RingBuffer(Object[] objArr, int i) {
        this.buffer = objArr;
        if (i >= 0) {
            if (i <= objArr.length) {
                this.capacity = objArr.length;
                this.size = i;
                return;
            } else {
                StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("ring buffer filled size: ", i, " cannot be larger than the buffer size: ");
                m.append(objArr.length);
                throw new IllegalArgumentException(m.toString().toString());
            }
        }
        throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("ring buffer filled size should not be negative but it is ", i).toString());
    }

    @Override // java.util.List
    public final Object get(int i) {
        AbstractList.Companion companion = AbstractList.Companion;
        int i2 = this.size;
        companion.getClass();
        AbstractList.Companion.checkElementIndex$kotlin_stdlib(i, i2);
        return this.buffer[(this.startIndex + i) % this.capacity];
    }

    @Override // kotlin.collections.AbstractCollection
    public final int getSize() {
        return this.size;
    }

    @Override // kotlin.collections.AbstractList, java.util.Collection, java.lang.Iterable, java.util.List
    public final Iterator iterator() {
        return new AbstractIterator() { // from class: kotlin.collections.RingBuffer$iterator$1
            public int count;
            public int index;

            {
                this.count = RingBuffer.this.getSize();
                this.index = RingBuffer.this.startIndex;
            }

            @Override // kotlin.collections.AbstractIterator
            public final void computeNext() {
                if (this.count == 0) {
                    this.state = State.Done;
                    return;
                }
                setNext(RingBuffer.this.buffer[this.index]);
                this.index = (this.index + 1) % RingBuffer.this.capacity;
                this.count--;
            }
        };
    }

    public final void removeFirst(int i) {
        boolean z;
        boolean z2 = true;
        if (i >= 0) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            if (i > this.size) {
                z2 = false;
            }
            if (z2) {
                if (i > 0) {
                    int i2 = this.startIndex;
                    int i3 = this.capacity;
                    int i4 = (i2 + i) % i3;
                    if (i2 > i4) {
                        Arrays.fill(this.buffer, i2, i3, (Object) null);
                        Arrays.fill(this.buffer, 0, i4, (Object) null);
                    } else {
                        Arrays.fill(this.buffer, i2, i4, (Object) null);
                    }
                    this.startIndex = i4;
                    this.size -= i;
                    return;
                }
                return;
            }
            StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("n shouldn't be greater than the buffer size: n = ", i, ", size = ");
            m.append(this.size);
            throw new IllegalArgumentException(m.toString().toString());
        }
        throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("n shouldn't be negative but it is ", i).toString());
    }

    @Override // kotlin.collections.AbstractCollection, java.util.Collection
    public final Object[] toArray() {
        return toArray(new Object[getSize()]);
    }

    @Override // kotlin.collections.AbstractCollection, java.util.Collection
    public final Object[] toArray(Object[] objArr) {
        if (objArr.length < getSize()) {
            objArr = Arrays.copyOf(objArr, getSize());
        }
        int size = getSize();
        int i = 0;
        int i2 = 0;
        for (int i3 = this.startIndex; i2 < size && i3 < this.capacity; i3++) {
            objArr[i2] = this.buffer[i3];
            i2++;
        }
        while (i2 < size) {
            objArr[i2] = this.buffer[i];
            i2++;
            i++;
        }
        if (objArr.length > getSize()) {
            objArr[getSize()] = null;
        }
        return objArr;
    }

    public RingBuffer(int i) {
        this(new Object[i], 0);
    }
}
