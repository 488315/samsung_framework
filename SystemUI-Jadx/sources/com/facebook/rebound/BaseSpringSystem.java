package com.facebook.rebound;

import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;
import com.facebook.rebound.Spring;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes2.dex */
public class BaseSpringSystem {
    public final SpringLooper mSpringLooper;
    public final Map mSpringRegistry = new HashMap();
    public final Set mActiveSprings = new CopyOnWriteArraySet();
    public final CopyOnWriteArraySet mListeners = new CopyOnWriteArraySet();
    public boolean mIdle = true;

    public BaseSpringSystem(SpringLooper springLooper) {
        if (springLooper != null) {
            this.mSpringLooper = springLooper;
            springLooper.mSpringSystem = this;
            return;
        }
        throw new IllegalArgumentException("springLooper is required");
    }

    public final void activateSpring(String str) {
        Spring spring = (Spring) ((HashMap) this.mSpringRegistry).get(str);
        if (spring != null) {
            ((CopyOnWriteArraySet) this.mActiveSprings).add(spring);
            if (this.mIdle) {
                this.mIdle = false;
                this.mSpringLooper.start();
                return;
            }
            return;
        }
        throw new IllegalArgumentException(PathParser$$ExternalSyntheticOutline0.m("springId ", str, " does not reference a registered spring"));
    }

    public final Spring createSpring() {
        Spring spring = new Spring(this);
        HashMap hashMap = (HashMap) this.mSpringRegistry;
        String str = spring.mId;
        if (!hashMap.containsKey(str)) {
            hashMap.put(str, spring);
            return spring;
        }
        throw new IllegalArgumentException("spring is already registered");
    }

    public final void loop(double d) {
        BaseSpringSystem baseSpringSystem;
        boolean z;
        CopyOnWriteArraySet copyOnWriteArraySet;
        Iterator it;
        double d2;
        double d3;
        Spring.PhysicsState physicsState;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        CopyOnWriteArraySet copyOnWriteArraySet2 = this.mListeners;
        Iterator it2 = copyOnWriteArraySet2.iterator();
        if (!it2.hasNext()) {
            CopyOnWriteArraySet copyOnWriteArraySet3 = (CopyOnWriteArraySet) this.mActiveSprings;
            Iterator it3 = copyOnWriteArraySet3.iterator();
            while (it3.hasNext()) {
                Spring spring = (Spring) it3.next();
                if (spring.isAtRest() && spring.mWasAtRest) {
                    z = false;
                } else {
                    z = true;
                }
                if (z) {
                    double d4 = d / 1000.0d;
                    boolean isAtRest = spring.isAtRest();
                    if (isAtRest && spring.mWasAtRest) {
                        copyOnWriteArraySet = copyOnWriteArraySet2;
                        it = it3;
                    } else {
                        if (d4 > 0.064d) {
                            d4 = 0.064d;
                        }
                        spring.mTimeAccumulator += d4;
                        SpringConfig springConfig = spring.mSpringConfig;
                        double d5 = springConfig.tension;
                        double d6 = springConfig.friction;
                        Spring.PhysicsState physicsState2 = spring.mCurrentState;
                        double d7 = physicsState2.position;
                        double d8 = physicsState2.velocity;
                        Spring.PhysicsState physicsState3 = spring.mTempState;
                        double d9 = physicsState3.position;
                        double d10 = physicsState3.velocity;
                        it = it3;
                        copyOnWriteArraySet = copyOnWriteArraySet2;
                        CopyOnWriteArraySet copyOnWriteArraySet4 = copyOnWriteArraySet3;
                        double d11 = d7;
                        double d12 = d9;
                        double d13 = d8;
                        while (true) {
                            d2 = spring.mTimeAccumulator;
                            d3 = d10;
                            physicsState = spring.mPreviousState;
                            if (d2 < 0.001d) {
                                break;
                            }
                            double d14 = d2 - 0.001d;
                            spring.mTimeAccumulator = d14;
                            if (d14 < 0.001d) {
                                physicsState.position = d11;
                                physicsState.velocity = d13;
                            }
                            double d15 = spring.mEndValue;
                            double d16 = ((d15 - d12) * d5) - (d6 * d13);
                            double d17 = (d16 * 0.001d * 0.5d) + d13;
                            double d18 = ((d15 - (((d13 * 0.001d) * 0.5d) + d11)) * d5) - (d6 * d17);
                            double d19 = (d18 * 0.001d * 0.5d) + d13;
                            double d20 = ((d15 - (((d17 * 0.001d) * 0.5d) + d11)) * d5) - (d6 * d19);
                            double d21 = (d19 * 0.001d) + d11;
                            double d22 = (d20 * 0.001d) + d13;
                            d11 = ((((d17 + d19) * 2.0d) + d13 + d22) * 0.16666666666666666d * 0.001d) + d11;
                            d13 += (((d18 + d20) * 2.0d) + d16 + (((d15 - d21) * d5) - (d6 * d22))) * 0.16666666666666666d * 0.001d;
                            d12 = d21;
                            d10 = d22;
                        }
                        physicsState3.position = d12;
                        physicsState3.velocity = d3;
                        physicsState2.position = d11;
                        physicsState2.velocity = d13;
                        if (d2 > 0.0d) {
                            double d23 = d2 / 0.001d;
                            double d24 = 1.0d - d23;
                            physicsState2.position = (physicsState.position * d24) + (d11 * d23);
                            physicsState2.velocity = (physicsState.velocity * d24) + (d13 * d23);
                        }
                        if (!spring.isAtRest()) {
                            z2 = isAtRest;
                        } else {
                            if (d5 > 0.0d) {
                                physicsState2.position = spring.mEndValue;
                            } else {
                                spring.mEndValue = physicsState2.position;
                            }
                            spring.setVelocity(0.0d);
                            z2 = true;
                        }
                        if (spring.mWasAtRest) {
                            z3 = false;
                            spring.mWasAtRest = false;
                            z4 = true;
                        } else {
                            z3 = false;
                            z4 = false;
                        }
                        if (z2) {
                            spring.mWasAtRest = true;
                            z5 = true;
                        } else {
                            z5 = z3;
                        }
                        Iterator it4 = spring.mListeners.iterator();
                        while (it4.hasNext()) {
                            SpringListener springListener = (SpringListener) it4.next();
                            if (z4) {
                                springListener.onSpringActivate(spring);
                            }
                            springListener.onSpringUpdate(spring);
                            if (z5) {
                                springListener.onSpringAtRest(spring);
                            }
                        }
                        copyOnWriteArraySet3 = copyOnWriteArraySet4;
                    }
                } else {
                    copyOnWriteArraySet = copyOnWriteArraySet2;
                    it = it3;
                    copyOnWriteArraySet3.remove(spring);
                }
                copyOnWriteArraySet2 = copyOnWriteArraySet;
                it3 = it;
            }
            CopyOnWriteArraySet copyOnWriteArraySet5 = copyOnWriteArraySet2;
            if (copyOnWriteArraySet3.isEmpty()) {
                baseSpringSystem = this;
                baseSpringSystem.mIdle = true;
            } else {
                baseSpringSystem = this;
            }
            Iterator it5 = copyOnWriteArraySet5.iterator();
            if (!it5.hasNext()) {
                if (baseSpringSystem.mIdle) {
                    baseSpringSystem.mSpringLooper.stop();
                    return;
                }
                return;
            }
            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(it5.next());
            throw null;
        }
        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(it2.next());
        throw null;
    }
}
