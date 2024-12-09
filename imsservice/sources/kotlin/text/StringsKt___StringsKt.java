package kotlin.text;

import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt___RangesKt;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: _Strings.kt */
/* loaded from: classes.dex */
public class StringsKt___StringsKt extends StringsKt___StringsJvmKt {
    @NotNull
    public static String take(@NotNull String str, int i) {
        int coerceAtMost;
        Intrinsics.checkNotNullParameter(str, "<this>");
        if (!(i >= 0)) {
            throw new IllegalArgumentException(("Requested character count " + i + " is less than zero.").toString());
        }
        coerceAtMost = RangesKt___RangesKt.coerceAtMost(i, str.length());
        String substring = str.substring(0, coerceAtMost);
        Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String…ing(startIndex, endIndex)");
        return substring;
    }
}
