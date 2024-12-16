package android.media.tv.tuner.filter;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes3.dex */
public abstract class FilterConfiguration {
    final Settings mSettings;

    public abstract int getType();

    FilterConfiguration(Settings settings) {
        this.mSettings = settings;
    }

    public Settings getSettings() {
        return this.mSettings;
    }
}
