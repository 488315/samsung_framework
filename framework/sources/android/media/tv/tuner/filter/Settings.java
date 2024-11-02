package android.media.tv.tuner.filter;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes2.dex */
public abstract class Settings {
    private final int mType;

    public Settings(int type) {
        this.mType = type;
    }

    public int getType() {
        return this.mType;
    }
}
