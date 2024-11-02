package com.samsung.android.nexus.base.utils.random;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public final class FloatRandom extends CachedRandom {
    public float[] mCache;

    public FloatRandom() {
        super(0.0d, 1.0d);
    }

    public final float get() {
        if (this.mIndex >= this.mIndexLimit) {
            this.mRewind++;
            this.mIndex = -1;
        }
        if (this.mNeedRefresh || this.mRewind >= 10) {
            this.mRewind = 0;
            this.mNeedRefresh = false;
            onRefreshCache();
            this.mIndex = -1;
        }
        float[] fArr = this.mCache;
        int i = this.mIndex + 1;
        this.mIndex = i;
        return fArr[i];
    }

    @Override // com.samsung.android.nexus.base.utils.random.CachedRandom
    public final void onCreate() {
        this.mCache = new float[100000];
    }

    public final void onRefreshCache() {
        float[] fArr = this.mCache;
        float f = (float) this.mMin;
        float f2 = (float) this.mMax;
        NexusRandom nexusRandom = CachedRandom.sRandom;
        nexusRandom.getClass();
        if (fArr != null && fArr.length > 0) {
            int i = this.mCacheSize + 0;
            if (i <= fArr.length) {
                float f3 = f2 - f;
                long j = nexusRandom.seed;
                for (int i2 = 0; i2 < i; i2++) {
                    j = ((j * 25214903917L) + 11) & 281474976710655L;
                    fArr[i2] = ((((int) (j >>> 24)) / 1.6777216E7f) * f3) + f;
                }
                nexusRandom.seed = j;
            }
        }
    }

    public FloatRandom(float f, float f2) {
        super(f, f2);
    }
}
