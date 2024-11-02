package com.samsung.android.nexus.particle.emitter;

import android.content.Context;
import com.samsung.android.nexus.particle.emitter.texture.ParticleTexture;
import java.util.ArrayList;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes3.dex */
public final class Emitter {
    public final EmissionRule mEmissionRule;
    public final ParticleRule mParticleRule;
    public World mWorld;
    public final boolean mEnable = true;
    public final ArrayList mEmitters = new ArrayList();
    public String subEmitterKey = "";
    public boolean isSubEmitter = false;

    public Emitter(Context context, EmissionRule emissionRule, ParticleRule particleRule) {
        this.mEmissionRule = emissionRule;
        this.mParticleRule = particleRule;
    }

    public final void destroy() {
        ParticleTexture particleTexture;
        ArrayList arrayList = this.mEmitters;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            Emitter emitter = (Emitter) arrayList.get(i);
            if (emitter != null) {
                emitter.destroy();
            }
        }
        ParticleRule particleRule = this.mParticleRule;
        if (particleRule != null && (particleTexture = particleRule.particleTexture) != null) {
            particleTexture.onDestroy();
        }
        arrayList.clear();
    }

    public Emitter(Context context, World world, EmissionRule emissionRule, ParticleRule particleRule) {
        this.mWorld = world;
        this.mEmissionRule = emissionRule;
        this.mParticleRule = particleRule;
    }
}
