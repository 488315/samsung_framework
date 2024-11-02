package kotlin.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.jvm.internal.ArrayIterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.EmptySequence;
import kotlin.sequences.Sequence;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public class ArraysKt___ArraysKt extends ArraysKt___ArraysJvmKt {
    public static final Sequence asSequence(final Object[] objArr) {
        boolean z;
        if (objArr.length == 0) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            return EmptySequence.INSTANCE;
        }
        return new Sequence() { // from class: kotlin.collections.ArraysKt___ArraysKt$asSequence$$inlined$Sequence$1
            @Override // kotlin.sequences.Sequence
            public final Iterator iterator() {
                return new ArrayIterator(objArr);
            }
        };
    }

    public static final boolean contains(Object[] objArr, Object obj) {
        int i;
        if (obj == null) {
            int length = objArr.length;
            i = 0;
            while (i < length) {
                if (objArr[i] == null) {
                    break;
                }
                i++;
            }
            i = -1;
        } else {
            int length2 = objArr.length;
            for (int i2 = 0; i2 < length2; i2++) {
                if (Intrinsics.areEqual(obj, objArr[i2])) {
                    i = i2;
                    break;
                }
            }
            i = -1;
        }
        return i >= 0;
    }

    public static final List filterNotNull(Object[] objArr) {
        ArrayList arrayList = new ArrayList();
        for (Object obj : objArr) {
            if (obj != null) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public static final Integer firstOrNull(int[] iArr) {
        boolean z;
        if (iArr.length == 0) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            return null;
        }
        return Integer.valueOf(iArr[0]);
    }

    public static final Object getOrNull(Object[] objArr) {
        if (objArr.length - 1 >= 0) {
            return objArr[0];
        }
        return null;
    }

    public static final List toList(Object[] objArr) {
        int length = objArr.length;
        if (length != 0) {
            if (length != 1) {
                return toMutableList(objArr);
            }
            return Collections.singletonList(objArr[0]);
        }
        return EmptyList.INSTANCE;
    }

    public static final List toMutableList(Object[] objArr) {
        return new ArrayList(new ArrayAsCollection(objArr, false));
    }

    public static final Set toSet(Object[] objArr) {
        int length = objArr.length;
        if (length == 0) {
            return EmptySet.INSTANCE;
        }
        if (length != 1) {
            LinkedHashSet linkedHashSet = new LinkedHashSet(MapsKt__MapsJVMKt.mapCapacity(objArr.length));
            for (Object obj : objArr) {
                linkedHashSet.add(obj);
            }
            return linkedHashSet;
        }
        return Collections.singleton(objArr[0]);
    }

    public static final boolean contains(int i, int[] iArr) {
        int length = iArr.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                i2 = -1;
                break;
            }
            if (i == iArr[i2]) {
                break;
            }
            i2++;
        }
        return i2 >= 0;
    }

    public static final Set toSet(int[] iArr) {
        int length = iArr.length;
        if (length == 0) {
            return EmptySet.INSTANCE;
        }
        if (length != 1) {
            LinkedHashSet linkedHashSet = new LinkedHashSet(MapsKt__MapsJVMKt.mapCapacity(iArr.length));
            for (int i : iArr) {
                linkedHashSet.add(Integer.valueOf(i));
            }
            return linkedHashSet;
        }
        return Collections.singleton(Integer.valueOf(iArr[0]));
    }
}
