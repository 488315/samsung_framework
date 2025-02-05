package android.media.tv.tuner.frontend;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes3.dex */
public class AtscFrontendCapabilities extends FrontendCapabilities {
    private final int mModulationCap;

    private AtscFrontendCapabilities(int modulationCap) {
        this.mModulationCap = modulationCap;
    }

    public int getModulationCapability() {
        return this.mModulationCap;
    }
}
