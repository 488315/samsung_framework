package kotlin.sequences;

import java.util.Iterator;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public final class TransformingIndexedSequence$iterator$1 implements Iterator, KMappedMarker {
    public int index;
    public final Iterator iterator;
    public final /* synthetic */ TransformingIndexedSequence this$0;

    public TransformingIndexedSequence$iterator$1(TransformingIndexedSequence transformingIndexedSequence) {
        this.this$0 = transformingIndexedSequence;
        this.iterator = transformingIndexedSequence.sequence.iterator();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.iterator.hasNext();
    }

    @Override // java.util.Iterator
    public final Object next() {
        Function2 function2 = this.this$0.transformer;
        int i = this.index;
        this.index = i + 1;
        if (i >= 0) {
            return function2.invoke(Integer.valueOf(i), this.iterator.next());
        }
        CollectionsKt__CollectionsKt.throwIndexOverflow();
        throw null;
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
