package com.android.server.location.provider;

import com.android.server.location.provider.AbstractLocationProvider;
import java.util.function.UnaryOperator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AbstractLocationProvider$$ExternalSyntheticLambda0 implements UnaryOperator {
    public final /* synthetic */ boolean f$0;

    public /* synthetic */ AbstractLocationProvider$$ExternalSyntheticLambda0(boolean z) {
        this.f$0 = z;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        boolean z = this.f$0;
        AbstractLocationProvider.State state = (AbstractLocationProvider.State) obj;
        return z == state.allowed ? state : new AbstractLocationProvider.State(z, state.properties, state.identity, state.extraAttributionTags);
    }
}
