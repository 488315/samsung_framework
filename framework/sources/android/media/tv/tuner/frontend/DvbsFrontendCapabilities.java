package android.media.tv.tuner.frontend;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes3.dex */
public class DvbsFrontendCapabilities extends FrontendCapabilities {
    private final long mInnerFecCap;
    private final int mModulationCap;
    private final int mStandard;

    private DvbsFrontendCapabilities(int modulationCap, long innerFecCap, int standard) {
        this.mModulationCap = modulationCap;
        this.mInnerFecCap = innerFecCap;
        this.mStandard = standard;
    }

    public int getModulationCapability() {
        return this.mModulationCap;
    }

    public long getInnerFecCapability() {
        return this.mInnerFecCap;
    }

    public int getStandardCapability() {
        return this.mStandard;
    }
}
