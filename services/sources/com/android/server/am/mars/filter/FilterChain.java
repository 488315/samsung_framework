package com.android.server.am.mars.filter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class FilterChain {
    public IFilter mFilter;
    public FilterChain nextFilterChain;

    public final int filter(int i, int i2, int i3, String str) {
        int filter = this.mFilter.filter(i, i2, i3, str);
        if (filter > 0) {
            return filter;
        }
        FilterChain filterChain = this.nextFilterChain;
        if (filterChain != null) {
            return filterChain.filter(i, i2, i3, str);
        }
        return 0;
    }
}
