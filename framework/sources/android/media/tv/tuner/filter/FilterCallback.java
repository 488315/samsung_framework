package android.media.tv.tuner.filter;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes3.dex */
public interface FilterCallback {
    void onFilterEvent(Filter filter, FilterEvent[] filterEventArr);

    void onFilterStatusChanged(Filter filter, int i);
}
