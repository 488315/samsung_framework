package com.android.server.timezonedetector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class GeolocationTimeZoneSuggestion {
    public final long mEffectiveFromElapsedMillis;
    public final List mZoneIds;

    public GeolocationTimeZoneSuggestion(long j, List list) {
        this.mEffectiveFromElapsedMillis = j;
        if (list == null) {
            this.mZoneIds = null;
        } else {
            this.mZoneIds = Collections.unmodifiableList(new ArrayList(list));
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || GeolocationTimeZoneSuggestion.class != obj.getClass()) {
            return false;
        }
        GeolocationTimeZoneSuggestion geolocationTimeZoneSuggestion = (GeolocationTimeZoneSuggestion) obj;
        return this.mEffectiveFromElapsedMillis == geolocationTimeZoneSuggestion.mEffectiveFromElapsedMillis && Objects.equals(this.mZoneIds, geolocationTimeZoneSuggestion.mZoneIds);
    }

    public final int hashCode() {
        return Objects.hash(Long.valueOf(this.mEffectiveFromElapsedMillis), this.mZoneIds);
    }

    public final String toString() {
        return "GeolocationTimeZoneSuggestion{mEffectiveFromElapsedMillis=" + this.mEffectiveFromElapsedMillis + ", mZoneIds=" + this.mZoneIds + '}';
    }
}
