package androidx.constraintlayout.core.motion.utils;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final class SpringStopEngine implements StopEngine {
    public float mLastTime;
    public float mMass;
    public float mPos;
    public double mStiffness;
    public float mStopThreshold;
    public double mTargetPos;
    public float mV;
    public double mDamping = 0.5d;
    public int mBoundaryMode = 0;

    @Override // androidx.constraintlayout.core.motion.utils.StopEngine
    public final float getInterpolation(float f) {
        SpringStopEngine springStopEngine = this;
        float f2 = f;
        double d = f2 - springStopEngine.mLastTime;
        double d2 = springStopEngine.mStiffness;
        double d3 = springStopEngine.mDamping;
        int sqrt = (int) ((9.0d / ((Math.sqrt(d2 / springStopEngine.mMass) * d) * 4.0d)) + 1.0d);
        double d4 = d / sqrt;
        int i = 0;
        while (i < sqrt) {
            double d5 = springStopEngine.mPos;
            double d6 = springStopEngine.mTargetPos;
            int i2 = sqrt;
            int i3 = i;
            double d7 = springStopEngine.mV;
            double d8 = springStopEngine.mMass;
            double d9 = ((((((-d2) * (d5 - d6)) - (d7 * d3)) / d8) * d4) / 2.0d) + d7;
            double d10 = ((((-((((d4 * d9) / 2.0d) + d5) - d6)) * d2) - (d9 * d3)) / d8) * d4;
            float f3 = (float) (d7 + d10);
            this.mV = f3;
            float f4 = (float) ((((d10 / 2.0d) + d7) * d4) + d5);
            this.mPos = f4;
            int i4 = this.mBoundaryMode;
            if (i4 > 0) {
                if (f4 < 0.0f && (i4 & 1) == 1) {
                    this.mPos = -f4;
                    this.mV = -f3;
                }
                float f5 = this.mPos;
                if (f5 > 1.0f && (i4 & 2) == 2) {
                    this.mPos = 2.0f - f5;
                    this.mV = -this.mV;
                }
            }
            f2 = f;
            sqrt = i2;
            i = i3 + 1;
            springStopEngine = this;
        }
        SpringStopEngine springStopEngine2 = springStopEngine;
        springStopEngine2.mLastTime = f2;
        return springStopEngine2.mPos;
    }

    @Override // androidx.constraintlayout.core.motion.utils.StopEngine
    public final float getVelocity() {
        return 0.0f;
    }

    @Override // androidx.constraintlayout.core.motion.utils.StopEngine
    public final boolean isStopped() {
        double d = this.mPos - this.mTargetPos;
        double d2 = this.mStiffness;
        double d3 = this.mV;
        if (Math.sqrt((((d2 * d) * d) + ((d3 * d3) * this.mMass)) / d2) <= this.mStopThreshold) {
            return true;
        }
        return false;
    }
}
