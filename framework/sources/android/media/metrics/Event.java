package android.media.metrics;

import android.os.Bundle;

/* loaded from: classes2.dex */
public abstract class Event {
    Bundle mMetricsBundle;
    final long mTimeSinceCreatedMillis;

    Event() {
        this.mMetricsBundle = new Bundle();
        this.mTimeSinceCreatedMillis = -1L;
    }

    Event(long timeSinceCreatedMillis, Bundle extras) {
        this.mMetricsBundle = new Bundle();
        this.mTimeSinceCreatedMillis = timeSinceCreatedMillis;
        this.mMetricsBundle = extras;
    }

    public long getTimeSinceCreatedMillis() {
        return this.mTimeSinceCreatedMillis;
    }

    public Bundle getMetricsBundle() {
        return this.mMetricsBundle;
    }
}
