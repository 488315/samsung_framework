package com.samsung.android.server.audio;

import com.samsung.android.server.audio.MicModeType;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class MicModeType$2$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ MicModeType$2$$ExternalSyntheticLambda0(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        int i2 = this.f$0;
        Integer num = (Integer) obj;
        switch (i) {
            case 0:
                int i3 = MicModeType.AnonymousClass2.$r8$clinit;
                if (i2 == num.intValue()) {
                    break;
                }
                break;
            case 1:
                int i4 = MicModeType.AnonymousClass5.$r8$clinit;
                if (i2 == num.intValue()) {
                    break;
                }
                break;
            default:
                int i5 = MicModeType.AnonymousClass6.$r8$clinit;
                if (i2 == num.intValue()) {
                    break;
                }
                break;
        }
        return true;
    }
}
