package android.widget;

import android.animation.ValueAnimator;
import android.compat.Compatibility;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.RenderNode;
import android.hardware.scontext.SContextConstants;
import android.media.audio.Enums;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import com.android.internal.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public class EdgeEffect {
    private static final double DAMPING_RATIO = 0.98d;
    private static final boolean DEBUG = false;
    private static final float EDGE_CONTROL_POINT_HEIGHT_NON_TAB_IN_DIP = 29.0f;
    private static final float EDGE_CONTROL_POINT_HEIGHT_TAB_IN_DIP = 19.0f;
    private static final String EDGE_GLOW_COLOR_DARK = "#fafafa";
    private static final String EDGE_GLOW_COLOR_LIGHT = "#000000";
    private static final float EDGE_MAX_ALPAH_DARK = 0.08f;
    private static final float EDGE_MAX_ALPAH_LIGHT = 0.05f;
    private static final float EDGE_PADDING_NON_TAB_IN_DIP = 5.0f;
    private static final float EDGE_PADDING_TAB_IN_DIP = 3.0f;
    private static final float EPSILON = 0.001f;
    private static final float EXP_STRETCH_INTENSITY = 0.016f;
    private static final float GLOW_ALPHA_START = 0.09f;
    private static final double LINEAR_DISTANCE_TAKE_OVER = 8.0d;
    private static final float LINEAR_STRETCH_INTENSITY = 0.016f;
    private static final float LINEAR_VELOCITY_TAKE_OVER = 200.0f;
    private static final float MAX_ALPHA = 0.15f;
    private static final float MAX_GLOW_SCALE = 2.0f;
    private static final int MAX_VELOCITY = 10000;
    private static final int MIN_VELOCITY = 100;
    private static final int MSG_CALL_ONRELEASE = 1;
    private static final double NATURAL_FREQUENCY = 24.657d;
    private static final float ON_ABSORB_VELOCITY_ADJUSTMENT = 2.0f;
    private static final int PULL_DECAY_TIME = 2000;
    private static final float PULL_DISTANCE_ALPHA_GLOW_FACTOR = 0.8f;
    private static final float PULL_GLOW_BEGIN = 0.0f;
    private static final int PULL_TIME = 167;
    private static final float RADIUS_FACTOR = 0.6f;
    private static final int RECEDE_TIME = 600;
    private static final float SCROLL_DIST_AFFECTED_BY_EXP_STRETCH = 0.33f;
    private static final int SEM_APPEAR_TIME = 250;
    private static final int SEM_KEEP_TIME = 0;
    private static final int SEM_RECEDE_TIME = 450;
    private static final int SEM_STATE_APPEAR = 5;
    private static final int SEM_STATE_KEEP = 6;
    private static final int STATE_ABSORB = 2;
    private static final int STATE_IDLE = 0;
    private static final int STATE_PULL = 1;
    private static final int STATE_PULL_DECAY = 4;
    private static final int STATE_RECEDE = 3;
    private static final float TAB_HEIGHT_BUFFER_IN_DIP = 5.0f;
    private static final String TAG = "EdgeEffect";
    private static final int TYPE_GLOW = 0;
    private static final int TYPE_NONE = -1;
    private static final int TYPE_STRETCH = 1;
    public static final long USE_STRETCH_EDGE_EFFECT_BY_DEFAULT = 171228096;
    private static final double VALUE_THRESHOLD = 0.001d;
    private static final int VELOCITY_GLOW_FACTOR = 6;
    private static final double VELOCITY_THRESHOLD = 0.01d;
    private float mBaseGlowScale;
    private final Rect mBounds;
    private float mDisplacement;
    private float mDistance;
    private float mDuration;
    private int mEdgeEffectType;
    private Runnable mForceCallOnRelease;
    private float mGlowAlpha;
    private float mGlowAlphaFinish;
    private float mGlowAlphaStart;
    private float mGlowScaleY;
    private float mGlowScaleYFinish;
    private float mGlowScaleYStart;
    private Handler mHandler;
    private float mHeight;
    private View mHostView;
    private final Interpolator mInterpolator;
    private final Paint mPaint;
    private final Path mPath;
    private float mPullDistance;
    private float mRadius;
    private long mStartTime;
    private int mState;
    private float mTargetDisplacement;
    private float mTempDeltaDistance;
    private float mTempDisplacement;
    private Matrix mTmpMatrix;
    private float[] mTmpPoints;
    private float mVelocity;
    private float mWidth;
    public static final BlendMode DEFAULT_BLEND_MODE = BlendMode.SRC_ATOP;
    private static final double ANGLE = 0.5235987755982988d;
    private static final float SIN = (float) Math.sin(ANGLE);
    private static final float COS = (float) Math.cos(ANGLE);

    @Retention(RetentionPolicy.SOURCE)
    public @interface EdgeEffectType {
    }

    public EdgeEffect(Context context) {
        this(context, null);
    }

    public EdgeEffect(Context context, AttributeSet attrs) {
        this.mInterpolator = new DecelerateInterpolator();
        this.mState = 0;
        this.mBounds = new Rect();
        this.mPaint = new Paint();
        this.mDisplacement = 0.5f;
        this.mTargetDisplacement = 0.5f;
        this.mEdgeEffectType = 0;
        this.mTmpMatrix = null;
        this.mTmpPoints = null;
        this.mPath = new Path();
        this.mHandler = new Handler(Looper.getMainLooper()) { // from class: android.widget.EdgeEffect.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        EdgeEffect.this.onRelease();
                        break;
                }
            }
        };
        this.mForceCallOnRelease = new Runnable() { // from class: android.widget.EdgeEffect.2
            @Override // java.lang.Runnable
            public void run() {
                EdgeEffect.this.onPull(EdgeEffect.this.mTempDeltaDistance, EdgeEffect.this.mTempDisplacement);
                EdgeEffect.this.mHandler.sendEmptyMessageDelayed(1, 700L);
            }
        };
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.EdgeEffect);
        int themeColor = a.getColor(0, -10066330);
        this.mEdgeEffectType = Compatibility.isChangeEnabled(USE_STRETCH_EDGE_EFFECT_BY_DEFAULT) ? 1 : 0;
        a.recycle();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setColor((16777215 & themeColor) | Enums.AUDIO_FORMAT_DTS_UHD_P2);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setBlendMode(DEFAULT_BLEND_MODE);
    }

    private float calculateEdgeEffectMargin(int width) {
        float margin = ((float) (width * 0.136d)) / 2.0f;
        return margin;
    }

    public void semSetHostView(View hostView, boolean canVerticalScroll) {
        this.mHostView = hostView;
    }

    private int getCurrentEdgeEffectBehavior() {
        if (!ValueAnimator.areAnimatorsEnabled()) {
            return -1;
        }
        return this.mEdgeEffectType;
    }

    public void setSize(int width, int height) {
        float r = (width * 0.6f) / SIN;
        float y = COS * r;
        float h = r - y;
        float or = (height * 0.6f) / SIN;
        float oy = COS * or;
        float oh = or - oy;
        this.mRadius = r;
        this.mBaseGlowScale = h > 0.0f ? Math.min(oh / h, 1.0f) : 1.0f;
        this.mBounds.set(this.mBounds.left, this.mBounds.top, width, (int) Math.min(height, h));
        this.mWidth = width;
        this.mHeight = height;
    }

    public boolean isFinished() {
        return this.mState == 0;
    }

    public void finish() {
        this.mState = 0;
        this.mDistance = 0.0f;
        this.mVelocity = 0.0f;
    }

    public void onPull(float deltaDistance) {
        onPull(deltaDistance, 0.5f);
    }

    private boolean isEdgeEffectRunning() {
        return this.mState == 5 || this.mState == 6 || this.mState == 3 || this.mState == 2;
    }

    public void onPullCallOnRelease(float deltaDistance, float displacement, int delayTime) {
        this.mTempDeltaDistance = deltaDistance;
        this.mTempDisplacement = displacement;
        this.mHandler.postDelayed(this.mForceCallOnRelease, delayTime);
    }

    public void onPull(float deltaDistance, float displacement) {
        int edgeEffectBehavior = getCurrentEdgeEffectBehavior();
        if (edgeEffectBehavior == -1) {
            finish();
            return;
        }
        long now = AnimationUtils.currentAnimationTimeMillis();
        this.mTargetDisplacement = displacement;
        if (this.mState == 4 && now - this.mStartTime < this.mDuration && this.mEdgeEffectType == 0) {
            return;
        }
        if (this.mState != 1) {
            if (this.mEdgeEffectType == 1) {
                this.mPullDistance = this.mDistance;
            } else {
                this.mGlowScaleY = Math.max(0.0f, this.mGlowScaleY);
            }
            if (this.mHostView != null && this.mGlowScaleY == 0.0f) {
                this.mHostView.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(28));
            }
        }
        this.mState = 1;
        this.mStartTime = now;
        this.mDuration = 167.0f;
        this.mPullDistance += deltaDistance;
        if (edgeEffectBehavior == 1) {
            this.mPullDistance = Math.min(1.0f, this.mPullDistance);
        }
        this.mDistance = Math.max(0.0f, this.mPullDistance);
        this.mVelocity = 0.0f;
        if (this.mPullDistance == 0.0f) {
            this.mGlowScaleYStart = 0.0f;
            this.mGlowScaleY = 0.0f;
            this.mGlowAlphaStart = 0.0f;
            this.mGlowAlpha = 0.0f;
        } else {
            float absdd = Math.abs(deltaDistance);
            float min = Math.min(MAX_ALPHA, this.mGlowAlpha + (0.8f * absdd));
            this.mGlowAlphaStart = min;
            this.mGlowAlpha = min;
            float scale = (float) (Math.max(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, (1.0d - (1.0d / Math.sqrt(Math.abs(this.mPullDistance) * this.mBounds.height()))) - 0.3d) / 0.7d);
            this.mGlowScaleYStart = scale;
            this.mGlowScaleY = scale;
        }
        float absdd2 = this.mGlowAlpha;
        this.mGlowAlphaFinish = absdd2;
        this.mGlowScaleYFinish = this.mGlowScaleY;
        if (edgeEffectBehavior == 1 && this.mDistance == 0.0f) {
            this.mState = 0;
        }
    }

    public float onPullDistance(float deltaDistance, float displacement) {
        int edgeEffectBehavior = getCurrentEdgeEffectBehavior();
        if (edgeEffectBehavior == -1) {
            return 0.0f;
        }
        float finalDistance = Math.max(0.0f, this.mDistance + deltaDistance);
        float delta = finalDistance - this.mDistance;
        if (delta == 0.0f && this.mDistance == 0.0f) {
            return 0.0f;
        }
        if (this.mState != 1 && this.mState != 4 && edgeEffectBehavior == 0) {
            this.mPullDistance = this.mDistance;
            this.mState = 1;
        }
        onPull(delta, displacement);
        return delta;
    }

    public float getDistance() {
        return this.mDistance;
    }

    public void onRelease() {
        this.mPullDistance = 0.0f;
        if (this.mState != 1 && this.mState != 4) {
            return;
        }
        this.mState = 3;
        this.mGlowAlphaStart = this.mGlowAlpha;
        this.mGlowScaleYStart = this.mGlowScaleY;
        this.mGlowAlphaFinish = 0.0f;
        this.mGlowScaleYFinish = 0.0f;
        this.mVelocity = 0.0f;
        this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
        this.mDuration = 600.0f;
    }

    public void onAbsorb(int velocity) {
        int edgeEffectBehavior = getCurrentEdgeEffectBehavior();
        if (this.mHostView != null) {
            this.mHostView.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(28));
        }
        if (edgeEffectBehavior == 1) {
            this.mState = 3;
            this.mVelocity = velocity * 2.0f;
            this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
        } else {
            if (edgeEffectBehavior == 0) {
                this.mState = 2;
                this.mVelocity = 0.0f;
                int velocity2 = Math.min(Math.max(100, Math.abs(velocity)), 10000);
                this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
                this.mDuration = (velocity2 * 0.02f) + MAX_ALPHA;
                this.mGlowAlphaStart = GLOW_ALPHA_START;
                this.mGlowScaleYStart = Math.max(this.mGlowScaleY, 0.0f);
                this.mGlowScaleYFinish = Math.min(((((velocity2 / 100) * velocity2) * 1.5E-4f) / 2.0f) + 0.025f, 1.0f);
                this.mGlowAlphaFinish = Math.max(this.mGlowAlphaStart, Math.min(velocity2 * 6 * 1.0E-5f, MAX_ALPHA));
                this.mTargetDisplacement = 0.5f;
                return;
            }
            finish();
        }
    }

    public void setColor(int color) {
        this.mPaint.setColor(color);
    }

    public void setBlendMode(BlendMode blendmode) {
        this.mPaint.setBlendMode(blendmode);
    }

    public int getColor() {
        return this.mPaint.getColor();
    }

    public BlendMode getBlendMode() {
        return this.mPaint.getBlendMode();
    }

    public boolean draw(Canvas canvas) {
        float vecY;
        boolean z;
        int edgeEffectBehavior = getCurrentEdgeEffectBehavior();
        if (edgeEffectBehavior == 0) {
            update();
            int count = canvas.save();
            float centerX = this.mBounds.centerX();
            float centerY = this.mBounds.height() - this.mRadius;
            canvas.scale(1.0f, Math.min(this.mGlowScaleY, 1.0f) * this.mBaseGlowScale, centerX, 0.0f);
            float displacement = Math.max(0.0f, Math.min(this.mDisplacement, 1.0f)) - 0.5f;
            float translateX = (this.mBounds.width() * displacement) / 2.0f;
            canvas.clipRect(this.mBounds);
            canvas.translate(translateX, 0.0f);
            this.mPaint.setAlpha((int) (this.mGlowAlpha * 255.0f));
            canvas.drawCircle(centerX, centerY, this.mRadius, this.mPaint);
            canvas.restoreToCount(count);
            vecY = 0.0f;
        } else if (edgeEffectBehavior != 1 || !(canvas instanceof RecordingCanvas)) {
            this.mState = 0;
            vecY = 0.0f;
            this.mDistance = 0.0f;
            this.mVelocity = 0.0f;
        } else {
            if (this.mState == 3) {
                updateSpring();
            }
            if (this.mDistance != 0.0f) {
                RecordingCanvas recordingCanvas = (RecordingCanvas) canvas;
                if (this.mTmpMatrix == null) {
                    this.mTmpMatrix = new Matrix();
                    this.mTmpPoints = new float[12];
                }
                recordingCanvas.getMatrix(this.mTmpMatrix);
                this.mTmpPoints[0] = 0.0f;
                this.mTmpPoints[1] = 0.0f;
                this.mTmpPoints[2] = this.mWidth;
                this.mTmpPoints[3] = 0.0f;
                this.mTmpPoints[4] = this.mWidth;
                this.mTmpPoints[5] = this.mHeight;
                this.mTmpPoints[6] = 0.0f;
                this.mTmpPoints[7] = this.mHeight;
                this.mTmpPoints[8] = this.mWidth * this.mDisplacement;
                this.mTmpPoints[9] = 0.0f;
                this.mTmpPoints[10] = this.mWidth * this.mDisplacement;
                this.mTmpPoints[11] = this.mHeight * this.mDistance;
                this.mTmpMatrix.mapPoints(this.mTmpPoints);
                RenderNode renderNode = recordingCanvas.mNode;
                float left = renderNode.getLeft() + min(this.mTmpPoints[0], this.mTmpPoints[2], this.mTmpPoints[4], this.mTmpPoints[6]);
                float top = renderNode.getTop() + min(this.mTmpPoints[1], this.mTmpPoints[3], this.mTmpPoints[5], this.mTmpPoints[7]);
                float right = renderNode.getLeft() + max(this.mTmpPoints[0], this.mTmpPoints[2], this.mTmpPoints[4], this.mTmpPoints[6]);
                float bottom = renderNode.getTop() + max(this.mTmpPoints[1], this.mTmpPoints[3], this.mTmpPoints[5], this.mTmpPoints[7]);
                float x = this.mTmpPoints[10] - this.mTmpPoints[8];
                float width = right - left;
                float vecX = dampStretchVector(Math.max(-1.0f, Math.min(1.0f, x / width)));
                float y = this.mTmpPoints[11] - this.mTmpPoints[9];
                float height = bottom - top;
                float vecY2 = dampStretchVector(Math.max(-1.0f, Math.min(1.0f, y / height)));
                boolean hasValidVectors = Float.isFinite(vecX) && Float.isFinite(vecY2);
                if (right > left && bottom > top && this.mWidth > 0.0f && this.mHeight > 0.0f && hasValidVectors) {
                    renderNode.stretch(vecX, vecY2, this.mWidth, this.mHeight);
                }
                vecY = 0.0f;
            } else {
                vecY = 0.0f;
            }
        }
        boolean oneLastFrame = false;
        if (this.mState != 3 || this.mDistance != vecY || this.mVelocity != vecY) {
            z = false;
        } else {
            z = false;
            this.mState = 0;
            oneLastFrame = true;
        }
        if (this.mState != 0 || oneLastFrame) {
            return true;
        }
        return z;
    }

    private float min(float f1, float f2, float f3, float f4) {
        float min = Math.min(f1, f2);
        return Math.min(Math.min(min, f3), f4);
    }

    private float max(float f1, float f2, float f3, float f4) {
        float max = Math.max(f1, f2);
        return Math.max(Math.max(max, f3), f4);
    }

    public int getMaxHeight() {
        return (int) this.mHeight;
    }

    private void update() {
        long time = AnimationUtils.currentAnimationTimeMillis();
        float t = Math.min((time - this.mStartTime) / this.mDuration, 1.0f);
        float interp = this.mInterpolator.getInterpolation(t);
        this.mGlowAlpha = this.mGlowAlphaStart + ((this.mGlowAlphaFinish - this.mGlowAlphaStart) * interp);
        this.mGlowScaleY = this.mGlowScaleYStart + ((this.mGlowScaleYFinish - this.mGlowScaleYStart) * interp);
        if (this.mState != 1) {
            this.mDistance = calculateDistanceFromGlowValues(this.mGlowScaleY, this.mGlowAlpha);
        }
        this.mDisplacement = (this.mDisplacement + this.mTargetDisplacement) / 2.0f;
        if (t >= 0.999f) {
            switch (this.mState) {
                case 1:
                    this.mState = 4;
                    this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
                    this.mDuration = 2000.0f;
                    this.mGlowAlphaStart = this.mGlowAlpha;
                    this.mGlowScaleYStart = this.mGlowScaleY;
                    this.mGlowAlphaFinish = 0.0f;
                    this.mGlowScaleYFinish = 0.0f;
                    break;
                case 2:
                    this.mState = 3;
                    this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
                    this.mDuration = 600.0f;
                    this.mGlowAlphaStart = this.mGlowAlpha;
                    this.mGlowScaleYStart = this.mGlowScaleY;
                    this.mGlowAlphaFinish = 0.0f;
                    this.mGlowScaleYFinish = 0.0f;
                    break;
                case 3:
                    this.mState = 0;
                    break;
                case 4:
                    this.mState = 3;
                    break;
            }
        }
    }

    private void updateSpring() {
        float f;
        long time = AnimationUtils.currentAnimationTimeMillis();
        float deltaT = (time - this.mStartTime) / 1000.0f;
        if (deltaT < 0.001f) {
            return;
        }
        this.mStartTime = time;
        if (Math.abs(this.mVelocity) <= 200.0f && Math.abs(this.mDistance * this.mHeight) < LINEAR_DISTANCE_TAKE_OVER && Math.signum(this.mVelocity) == (-Math.signum(this.mDistance))) {
            this.mVelocity = Math.signum(this.mVelocity) * 200.0f;
            float targetDistance = this.mDistance + ((this.mVelocity * deltaT) / this.mHeight);
            if (Math.signum(targetDistance) != Math.signum(this.mDistance)) {
                this.mDistance = 0.0f;
                this.mVelocity = 0.0f;
                return;
            } else {
                this.mDistance = targetDistance;
                return;
            }
        }
        double mDampedFreq = Math.sqrt(0.03960000000000008d) * NATURAL_FREQUENCY;
        double cosCoeff = this.mDistance * this.mHeight;
        double sinCoeff = (1.0d / mDampedFreq) * ((this.mDistance * 24.16386d * this.mHeight) + this.mVelocity);
        double distance = Math.pow(2.718281828459045d, deltaT * (-24.16386d)) * ((Math.cos(deltaT * mDampedFreq) * cosCoeff) + (Math.sin(deltaT * mDampedFreq) * sinCoeff));
        double velocity = ((-24.657d) * distance * DAMPING_RATIO) + (Math.pow(2.718281828459045d, deltaT * (-24.16386d)) * (((-mDampedFreq) * cosCoeff * Math.sin(deltaT * mDampedFreq)) + (mDampedFreq * sinCoeff * Math.cos(deltaT * mDampedFreq))));
        this.mDistance = ((float) distance) / this.mHeight;
        this.mVelocity = (float) velocity;
        if (this.mDistance <= 1.0f) {
            f = 0.0f;
        } else {
            this.mDistance = 1.0f;
            f = 0.0f;
            this.mVelocity = 0.0f;
        }
        if (isAtEquilibrium()) {
            this.mDistance = f;
            this.mVelocity = f;
        }
    }

    private float calculateDistanceFromGlowValues(float scale, float alpha) {
        if (scale >= 1.0f) {
            return 1.0f;
        }
        if (scale > 0.0f) {
            float v = 1.4285715f / (this.mGlowScaleY - 1.0f);
            return (v * v) / this.mBounds.height();
        }
        return alpha / 0.8f;
    }

    private boolean isAtEquilibrium() {
        double displacement = this.mDistance * this.mHeight;
        double velocity = this.mVelocity;
        return displacement < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN || (Math.abs(velocity) < VELOCITY_THRESHOLD && displacement < VALUE_THRESHOLD);
    }

    private float dampStretchVector(float normalizedVec) {
        float sign = normalizedVec > 0.0f ? 1.0f : -1.0f;
        float overscroll = Math.abs(normalizedVec);
        float linearIntensity = 0.016f * overscroll;
        double expIntensity = (1.0d - Math.exp((-overscroll) * 8.237217334679498d)) * 0.01600000075995922d;
        return ((float) (linearIntensity + expIntensity)) * sign;
    }
}
