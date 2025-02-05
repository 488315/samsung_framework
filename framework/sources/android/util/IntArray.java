package android.util;

import com.android.internal.util.ArrayUtils;
import com.android.internal.util.Preconditions;
import com.samsung.android.knox.analytics.database.Contract;
import java.util.Arrays;

/* loaded from: classes4.dex */
public class IntArray implements Cloneable {
    private static final int MIN_CAPACITY_INCREMENT = 12;
    private int mSize;
    private int[] mValues;

    private IntArray(int[] array, int size) {
        this.mValues = array;
        this.mSize = Preconditions.checkArgumentInRange(size, 0, array.length, Contract.DatabaseSize.PATH);
    }

    public IntArray() {
        this(0);
    }

    public IntArray(int initialCapacity) {
        if (initialCapacity == 0) {
            this.mValues = EmptyArray.INT;
        } else {
            this.mValues = ArrayUtils.newUnpaddedIntArray(initialCapacity);
        }
        this.mSize = 0;
    }

    public static IntArray wrap(int[] array) {
        return new IntArray(array, array.length);
    }

    public static IntArray fromArray(int[] array, int size) {
        return wrap(Arrays.copyOf(array, size));
    }

    public void resize(int newSize) {
        Preconditions.checkArgumentNonnegative(newSize);
        if (newSize <= this.mValues.length) {
            Arrays.fill(this.mValues, newSize, this.mValues.length, 0);
        } else {
            ensureCapacity(newSize - this.mSize);
        }
        this.mSize = newSize;
    }

    public void add(int value) {
        add(this.mSize, value);
    }

    public void add(int index, int value) {
        ensureCapacity(1);
        int rightSegment = this.mSize - index;
        this.mSize++;
        ArrayUtils.checkBounds(this.mSize, index);
        if (rightSegment != 0) {
            System.arraycopy(this.mValues, index, this.mValues, index + 1, rightSegment);
        }
        this.mValues[index] = value;
    }

    public int binarySearch(int value) {
        return ContainerHelpers.binarySearch(this.mValues, this.mSize, value);
    }

    public void addAll(IntArray values) {
        int count = values.mSize;
        ensureCapacity(count);
        System.arraycopy(values.mValues, 0, this.mValues, this.mSize, count);
        this.mSize += count;
    }

    public void addAll(int[] values) {
        int count = values.length;
        ensureCapacity(count);
        System.arraycopy(values, 0, this.mValues, this.mSize, count);
        this.mSize += count;
    }

    private void ensureCapacity(int count) {
        int currentSize = this.mSize;
        int minCapacity = currentSize + count;
        if (minCapacity >= this.mValues.length) {
            int targetCap = (currentSize < 6 ? 12 : currentSize >> 1) + currentSize;
            int newCapacity = targetCap > minCapacity ? targetCap : minCapacity;
            int[] newValues = ArrayUtils.newUnpaddedIntArray(newCapacity);
            System.arraycopy(this.mValues, 0, newValues, 0, currentSize);
            this.mValues = newValues;
        }
    }

    public void clear() {
        this.mSize = 0;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public IntArray m5208clone() {
        return new IntArray((int[]) this.mValues.clone(), this.mSize);
    }

    public int get(int index) {
        ArrayUtils.checkBounds(this.mSize, index);
        return this.mValues[index];
    }

    public void set(int index, int value) {
        ArrayUtils.checkBounds(this.mSize, index);
        this.mValues[index] = value;
    }

    public int indexOf(int value) {
        int n = this.mSize;
        for (int i = 0; i < n; i++) {
            if (this.mValues[i] == value) {
                return i;
            }
        }
        return -1;
    }

    public boolean contains(int value) {
        return indexOf(value) != -1;
    }

    public void remove(int index) {
        ArrayUtils.checkBounds(this.mSize, index);
        System.arraycopy(this.mValues, index + 1, this.mValues, index, (this.mSize - index) - 1);
        this.mSize--;
    }

    public int size() {
        return this.mSize;
    }

    public int[] toArray() {
        return Arrays.copyOf(this.mValues, this.mSize);
    }

    public String toString() {
        int iMax = this.mSize - 1;
        if (iMax == -1) {
            return "[]";
        }
        StringBuilder b = new StringBuilder();
        b.append('[');
        int i = 0;
        while (true) {
            b.append(this.mValues[i]);
            if (i == iMax) {
                return b.append(']').toString();
            }
            b.append(", ");
            i++;
        }
    }
}
