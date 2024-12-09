package kotlin.comparisons;

import java.util.Comparator;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: Comparisons.kt */
/* loaded from: classes.dex */
final class ReverseOrderComparator implements Comparator<Comparable<? super Object>> {

    @NotNull
    public static final ReverseOrderComparator INSTANCE = new ReverseOrderComparator();

    private ReverseOrderComparator() {
    }

    @Override // java.util.Comparator
    public /* bridge */ /* synthetic */ int compare(Comparable<? super Object> comparable, Comparable<? super Object> comparable2) {
        return compare2((Comparable<Object>) comparable, (Comparable<Object>) comparable2);
    }

    /* renamed from: compare, reason: avoid collision after fix types in other method */
    public int compare2(@NotNull Comparable<Object> a, @NotNull Comparable<Object> b) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        return b.compareTo(a);
    }

    @Override // java.util.Comparator
    @NotNull
    public final Comparator<Comparable<? super Object>> reversed() {
        return NaturalOrderComparator.INSTANCE;
    }
}
