package com.android.server.tv.tunerresourcemanager;

import com.android.server.tv.tunerresourcemanager.TunerResourceBasic;

/* loaded from: classes3.dex */
public final class LnbResource extends TunerResourceBasic {
    public LnbResource(Builder builder) {
        super(builder);
    }

    public String toString() {
        return "LnbResource[handle=" + this.mHandle + ", isInUse=" + this.mIsInUse + ", ownerClientId=" + this.mOwnerClientId + "]";
    }

    /* loaded from: classes3.dex */
    public class Builder extends TunerResourceBasic.Builder {
        public Builder(int i) {
            super(i);
        }

        public LnbResource build() {
            return new LnbResource(this);
        }
    }
}
