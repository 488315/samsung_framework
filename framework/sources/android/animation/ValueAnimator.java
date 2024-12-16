package android.animation;

import android.animation.AnimationHandler;
import android.animation.Animator;
import android.os.Looper;
import android.os.SystemProperties;
import android.os.Trace;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class ValueAnimator extends Animator implements AnimationHandler.AnimationFrameCallback {
    private static final boolean DEBUG = false;
    public static final int INFINITE = -1;
    public static final int RESTART = 1;
    public static final int REVERSE = 2;
    private static final String TAG = "ValueAnimator";
    private AnimationHandler mAnimationHandler;
    private long mPauseTime;
    private boolean mReversing;
    boolean mStartTimeCommitted;
    PropertyValuesHolder[] mValues;
    HashMap<String, PropertyValuesHolder> mValuesMap;
    private static final boolean TRACE_ANIMATION_FRACTION = SystemProperties.getBoolean("persist.debug.animator.trace_fraction", false);
    private static float sDurationScale = 1.0f;
    private static final ArrayList<WeakReference<DurationScaleChangeListener>> sDurationScaleChangeListeners = new ArrayList<>();
    private static final TimeInterpolator sDefaultInterpolator = new AccelerateDecelerateInterpolator();
    long mStartTime = -1;
    float mSeekFraction = -1.0f;
    private boolean mResumed = false;
    private float mOverallFraction = 0.0f;
    private float mCurrentFraction = 0.0f;
    private long mLastFrameTime = -1;
    private long mFirstFrameTime = -1;
    private boolean mRunning = false;
    private boolean mStarted = false;
    boolean mInitialized = false;
    private boolean mAnimationEndRequested = false;
    private long mDuration = 300;
    private long mStartDelay = 0;
    private int mRepeatCount = 0;
    private int mRepeatMode = 1;
    private boolean mSelfPulse = true;
    private boolean mSuppressSelfPulseRequested = false;
    private TimeInterpolator mInterpolator = sDefaultInterpolator;
    ArrayList<AnimatorUpdateListener> mUpdateListeners = null;
    private float mDurationScale = -1.0f;

    public interface AnimatorUpdateListener {
        void onAnimationUpdate(ValueAnimator valueAnimator);
    }

    public interface DurationScaleChangeListener {
        void onChanged(float f);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RepeatMode {
    }

    public static void setDurationScale(float durationScale) {
        List<WeakReference<DurationScaleChangeListener>> listenerCopy;
        sDurationScale = durationScale;
        synchronized (sDurationScaleChangeListeners) {
            listenerCopy = new ArrayList<>(sDurationScaleChangeListeners);
        }
        int listenersSize = listenerCopy.size();
        for (int i = 0; i < listenersSize; i++) {
            DurationScaleChangeListener listener = listenerCopy.get(i).get();
            if (listener != null) {
                listener.onChanged(durationScale);
            }
        }
    }

    public static float getDurationScale() {
        return sDurationScale;
    }

    public static boolean registerDurationScaleChangeListener(DurationScaleChangeListener listener) {
        int posToReplace = -1;
        synchronized (sDurationScaleChangeListeners) {
            for (int i = 0; i < sDurationScaleChangeListeners.size(); i++) {
                WeakReference<DurationScaleChangeListener> ref = sDurationScaleChangeListeners.get(i);
                if (ref.get() == null) {
                    if (posToReplace == -1) {
                        posToReplace = i;
                    }
                } else if (ref.get() == listener) {
                    return false;
                }
            }
            if (posToReplace != -1) {
                sDurationScaleChangeListeners.set(posToReplace, new WeakReference<>(listener));
                return true;
            }
            return sDurationScaleChangeListeners.add(new WeakReference<>(listener));
        }
    }

    public static boolean unregisterDurationScaleChangeListener(DurationScaleChangeListener listener) {
        boolean remove;
        synchronized (sDurationScaleChangeListeners) {
            WeakReference<DurationScaleChangeListener> listenerRefToRemove = null;
            Iterator<WeakReference<DurationScaleChangeListener>> it = sDurationScaleChangeListeners.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                WeakReference<DurationScaleChangeListener> listenerRef = it.next();
                if (listenerRef.get() == listener) {
                    listenerRefToRemove = listenerRef;
                    break;
                }
            }
            remove = sDurationScaleChangeListeners.remove(listenerRefToRemove);
        }
        return remove;
    }

    public static boolean areAnimatorsEnabled() {
        return sDurationScale != 0.0f;
    }

    public static ValueAnimator ofInt(int... values) {
        ValueAnimator anim = new ValueAnimator();
        anim.setIntValues(values);
        return anim;
    }

    public static ValueAnimator ofArgb(int... values) {
        ValueAnimator anim = new ValueAnimator();
        anim.setIntValues(values);
        anim.setEvaluator(ArgbEvaluator.getInstance());
        return anim;
    }

    public static ValueAnimator ofFloat(float... values) {
        ValueAnimator anim = new ValueAnimator();
        anim.setFloatValues(values);
        return anim;
    }

    public static ValueAnimator ofPropertyValuesHolder(PropertyValuesHolder... values) {
        ValueAnimator anim = new ValueAnimator();
        anim.setValues(values);
        return anim;
    }

    public static ValueAnimator ofObject(TypeEvaluator evaluator, Object... values) {
        ValueAnimator anim = new ValueAnimator();
        anim.setObjectValues(values);
        anim.setEvaluator(evaluator);
        return anim;
    }

    public void setIntValues(int... values) {
        if (values == null || values.length == 0) {
            return;
        }
        if (this.mValues == null || this.mValues.length == 0) {
            setValues(PropertyValuesHolder.ofInt("", values));
        } else {
            PropertyValuesHolder valuesHolder = this.mValues[0];
            valuesHolder.setIntValues(values);
        }
        this.mInitialized = false;
    }

    public void setFloatValues(float... values) {
        if (values == null || values.length == 0) {
            return;
        }
        if (this.mValues == null || this.mValues.length == 0) {
            setValues(PropertyValuesHolder.ofFloat("", values));
        } else {
            PropertyValuesHolder valuesHolder = this.mValues[0];
            valuesHolder.setFloatValues(values);
        }
        this.mInitialized = false;
    }

    public void setObjectValues(Object... values) {
        if (values == null || values.length == 0) {
            return;
        }
        if (this.mValues == null || this.mValues.length == 0) {
            setValues(PropertyValuesHolder.ofObject("", (TypeEvaluator) null, values));
        } else {
            PropertyValuesHolder valuesHolder = this.mValues[0];
            valuesHolder.setObjectValues(values);
        }
        this.mInitialized = false;
    }

    public void setValues(PropertyValuesHolder... values) {
        int numValues = values.length;
        this.mValues = values;
        this.mValuesMap = new HashMap<>(numValues);
        for (PropertyValuesHolder valuesHolder : values) {
            this.mValuesMap.put(valuesHolder.getPropertyName(), valuesHolder);
        }
        this.mInitialized = false;
    }

    public PropertyValuesHolder[] getValues() {
        return this.mValues;
    }

    void initAnimation() {
        if (!this.mInitialized) {
            if (this.mValues != null) {
                int numValues = this.mValues.length;
                for (int i = 0; i < numValues; i++) {
                    this.mValues[i].init();
                }
            }
            this.mInitialized = true;
        }
    }

    @Override // android.animation.Animator
    public ValueAnimator setDuration(long duration) {
        if (duration < 0) {
            throw new IllegalArgumentException("Animators cannot have negative duration: " + duration);
        }
        this.mDuration = duration;
        return this;
    }

    public void overrideDurationScale(float durationScale) {
        this.mDurationScale = durationScale;
    }

    private float resolveDurationScale() {
        return this.mDurationScale >= 0.0f ? this.mDurationScale : sDurationScale;
    }

    private long getScaledDuration() {
        return (long) (this.mDuration * resolveDurationScale());
    }

    @Override // android.animation.Animator
    public long getDuration() {
        return this.mDuration;
    }

    @Override // android.animation.Animator
    public long getTotalDuration() {
        if (this.mRepeatCount == -1) {
            return -1L;
        }
        return this.mStartDelay + (this.mDuration * (this.mRepeatCount + 1));
    }

    public void setCurrentPlayTime(long playTime) {
        float fraction = this.mDuration > 0 ? playTime / this.mDuration : 1.0f;
        setCurrentFraction(fraction);
    }

    public void setCurrentFraction(float fraction) {
        initAnimation();
        float fraction2 = clampFraction(fraction);
        this.mStartTimeCommitted = true;
        if (isPulsingInternal()) {
            long seekTime = (long) (getScaledDuration() * fraction2);
            long currentTime = AnimationUtils.currentAnimationTimeMillis();
            this.mStartTime = currentTime - seekTime;
        } else {
            this.mSeekFraction = fraction2;
        }
        this.mOverallFraction = fraction2;
        float currentIterationFraction = getCurrentIterationFraction(fraction2, this.mReversing);
        animateValue(currentIterationFraction);
    }

    private int getCurrentIteration(float fraction) {
        float fraction2 = clampFraction(fraction);
        double iteration = Math.floor(fraction2);
        if (fraction2 == iteration && fraction2 > 0.0f) {
            iteration -= 1.0d;
        }
        return (int) iteration;
    }

    private float getCurrentIterationFraction(float fraction, boolean inReverse) {
        float fraction2 = clampFraction(fraction);
        int iteration = getCurrentIteration(fraction2);
        float currentFraction = fraction2 - iteration;
        return shouldPlayBackward(iteration, inReverse) ? 1.0f - currentFraction : currentFraction;
    }

    private float clampFraction(float fraction) {
        if (fraction < 0.0f) {
            return 0.0f;
        }
        if (this.mRepeatCount != -1) {
            return Math.min(fraction, this.mRepeatCount + 1);
        }
        return fraction;
    }

    private boolean shouldPlayBackward(int iteration, boolean inReverse) {
        if (iteration <= 0 || this.mRepeatMode != 2 || (iteration >= this.mRepeatCount + 1 && this.mRepeatCount != -1)) {
            return inReverse;
        }
        return inReverse ? iteration % 2 == 0 : iteration % 2 != 0;
    }

    public long getCurrentPlayTime() {
        if (!this.mInitialized) {
            return 0L;
        }
        if (!this.mStarted && this.mSeekFraction < 0.0f) {
            return 0L;
        }
        if (this.mSeekFraction >= 0.0f) {
            return (long) (this.mDuration * this.mSeekFraction);
        }
        float durationScale = resolveDurationScale();
        if (durationScale == 0.0f) {
            durationScale = 1.0f;
        }
        return (long) ((AnimationUtils.currentAnimationTimeMillis() - this.mStartTime) / durationScale);
    }

    @Override // android.animation.Animator
    public long getStartDelay() {
        return this.mStartDelay;
    }

    @Override // android.animation.Animator
    public void setStartDelay(long startDelay) {
        if (startDelay < 0) {
            Log.w(TAG, "Start delay should always be non-negative");
            startDelay = 0;
        }
        this.mStartDelay = startDelay;
    }

    public static long getFrameDelay() {
        AnimationHandler.getInstance();
        return AnimationHandler.getFrameDelay();
    }

    public static void setFrameDelay(long frameDelay) {
        AnimationHandler.getInstance();
        AnimationHandler.setFrameDelay(frameDelay);
    }

    public Object getAnimatedValue() {
        if (this.mValues != null && this.mValues.length > 0) {
            return this.mValues[0].getAnimatedValue();
        }
        return null;
    }

    public Object getAnimatedValue(String propertyName) {
        PropertyValuesHolder valuesHolder = this.mValuesMap.get(propertyName);
        if (valuesHolder != null) {
            return valuesHolder.getAnimatedValue();
        }
        return null;
    }

    public void setRepeatCount(int value) {
        this.mRepeatCount = value;
    }

    public int getRepeatCount() {
        return this.mRepeatCount;
    }

    public void setRepeatMode(int value) {
        this.mRepeatMode = value;
    }

    public int getRepeatMode() {
        return this.mRepeatMode;
    }

    public void addUpdateListener(AnimatorUpdateListener listener) {
        if (this.mUpdateListeners == null) {
            this.mUpdateListeners = new ArrayList<>();
        }
        this.mUpdateListeners.add(listener);
    }

    public void removeAllUpdateListeners() {
        if (this.mUpdateListeners == null) {
            return;
        }
        this.mUpdateListeners.clear();
        this.mUpdateListeners = null;
    }

    public void removeUpdateListener(AnimatorUpdateListener listener) {
        if (this.mUpdateListeners == null) {
            return;
        }
        this.mUpdateListeners.remove(listener);
        if (this.mUpdateListeners.size() == 0) {
            this.mUpdateListeners = null;
        }
    }

    @Override // android.animation.Animator
    public void setInterpolator(TimeInterpolator value) {
        if (value != null) {
            this.mInterpolator = value;
        } else {
            this.mInterpolator = new LinearInterpolator();
        }
    }

    @Override // android.animation.Animator
    public TimeInterpolator getInterpolator() {
        return this.mInterpolator;
    }

    public void setEvaluator(TypeEvaluator value) {
        if (value != null && this.mValues != null && this.mValues.length > 0) {
            this.mValues[0].setEvaluator(value);
        }
    }

    private void start(boolean playBackwards) {
        if (Looper.myLooper() == null) {
            throw new AndroidRuntimeException("Animators may only be run on Looper threads");
        }
        this.mReversing = playBackwards;
        this.mSelfPulse = !this.mSuppressSelfPulseRequested;
        if (playBackwards && this.mSeekFraction != -1.0f && this.mSeekFraction != 0.0f) {
            if (this.mRepeatCount == -1) {
                float fraction = (float) (this.mSeekFraction - Math.floor(this.mSeekFraction));
                this.mSeekFraction = 1.0f - fraction;
            } else {
                this.mSeekFraction = (this.mRepeatCount + 1) - this.mSeekFraction;
            }
        }
        this.mStarted = true;
        this.mPaused = false;
        this.mRunning = false;
        this.mAnimationEndRequested = false;
        this.mLastFrameTime = -1L;
        this.mFirstFrameTime = -1L;
        this.mStartTime = -1L;
        addAnimationCallback(0L);
        if (this.mStartDelay == 0 || this.mSeekFraction >= 0.0f || this.mReversing) {
            startAnimation();
            if (this.mSeekFraction == -1.0f) {
                setCurrentPlayTime(0L);
            } else {
                setCurrentFraction(this.mSeekFraction);
            }
        }
    }

    @Override // android.animation.Animator
    void startWithoutPulsing(boolean inReverse) {
        this.mSuppressSelfPulseRequested = true;
        if (inReverse) {
            reverse();
        } else {
            start();
        }
        this.mSuppressSelfPulseRequested = false;
    }

    @Override // android.animation.Animator
    public void start() {
        start(false);
    }

    @Override // android.animation.Animator
    public void cancel() {
        if (Looper.myLooper() == null) {
            throw new AndroidRuntimeException("Animators may only be run on Looper threads");
        }
        if (this.mAnimationEndRequested) {
            return;
        }
        if ((this.mStarted || this.mRunning || this.mStartListenersCalled) && this.mListeners != null) {
            if (!this.mRunning) {
                notifyStartListeners(this.mReversing);
            }
            notifyListeners(Animator.AnimatorCaller.ON_CANCEL, false);
        }
        endAnimation();
    }

    @Override // android.animation.Animator
    public void end() {
        if (Looper.myLooper() == null) {
            throw new AndroidRuntimeException("Animators may only be run on Looper threads");
        }
        if (!this.mRunning) {
            startAnimation();
            this.mStarted = true;
        } else if (!this.mInitialized) {
            initAnimation();
        }
        animateValue(shouldPlayBackward(this.mRepeatCount, this.mReversing) ? 0.0f : 1.0f);
        endAnimation();
    }

    @Override // android.animation.Animator
    public void resume() {
        if (Looper.myLooper() == null) {
            throw new AndroidRuntimeException("Animators may only be resumed from the same thread that the animator was started on");
        }
        if (this.mPaused && !this.mResumed) {
            this.mResumed = true;
            if (this.mPauseTime > 0) {
                addAnimationCallback(0L);
            }
        }
        super.resume();
    }

    @Override // android.animation.Animator
    public void pause() {
        boolean previouslyPaused = this.mPaused;
        super.pause();
        if (!previouslyPaused && this.mPaused) {
            this.mPauseTime = -1L;
            this.mResumed = false;
        }
    }

    @Override // android.animation.Animator
    public boolean isRunning() {
        return this.mRunning;
    }

    @Override // android.animation.Animator
    public boolean isStarted() {
        return this.mStarted;
    }

    @Override // android.animation.Animator
    public void reverse() {
        if (!isPulsingInternal()) {
            if (this.mStarted) {
                this.mReversing = !this.mReversing;
                end();
                return;
            } else {
                start(true);
                return;
            }
        }
        long currentTime = AnimationUtils.currentAnimationTimeMillis();
        long currentPlayTime = currentTime - this.mStartTime;
        long timeLeft = getScaledDuration() - currentPlayTime;
        this.mStartTime = currentTime - timeLeft;
        this.mStartTimeCommitted = true;
        this.mReversing = !this.mReversing;
    }

    @Override // android.animation.Animator
    public boolean canReverse() {
        return true;
    }

    private void endAnimation() {
        if (this.mAnimationEndRequested) {
            return;
        }
        removeAnimationCallback();
        boolean notify = true;
        this.mAnimationEndRequested = true;
        this.mPaused = false;
        if ((!this.mStarted && !this.mRunning) || this.mListeners == null) {
            notify = false;
        }
        if (notify && !this.mRunning) {
            notifyStartListeners(this.mReversing);
        }
        this.mLastFrameTime = -1L;
        this.mFirstFrameTime = -1L;
        this.mStartTime = -1L;
        this.mRunning = false;
        this.mStarted = false;
        notifyEndListeners(this.mReversing);
        this.mReversing = false;
        if (Trace.isTagEnabled(8L)) {
            Trace.asyncTraceEnd(8L, getNameForTrace(), System.identityHashCode(this));
        }
    }

    private void startAnimation() {
        if (Trace.isTagEnabled(8L)) {
            Trace.asyncTraceBegin(8L, getNameForTrace(), System.identityHashCode(this));
        }
        this.mAnimationEndRequested = false;
        initAnimation();
        this.mRunning = true;
        if (this.mSeekFraction >= 0.0f) {
            this.mOverallFraction = this.mSeekFraction;
        } else {
            this.mOverallFraction = 0.0f;
        }
        notifyStartListeners(this.mReversing);
    }

    private boolean isPulsingInternal() {
        return this.mLastFrameTime >= 0;
    }

    String getNameForTrace() {
        return "animator";
    }

    @Override // android.animation.AnimationHandler.AnimationFrameCallback
    public void commitAnimationFrame(long frameTime) {
        if (!this.mStartTimeCommitted) {
            this.mStartTimeCommitted = true;
            long adjustment = frameTime - this.mLastFrameTime;
            if (adjustment > 0) {
                this.mStartTime += adjustment;
            }
        }
    }

    boolean animateBasedOnTime(long currentTime) {
        boolean done = false;
        if (this.mRunning) {
            long scaledDuration = getScaledDuration();
            float fraction = scaledDuration > 0 ? (currentTime - this.mStartTime) / scaledDuration : 1.0f;
            float lastFraction = this.mOverallFraction;
            boolean newIteration = ((int) fraction) > ((int) lastFraction);
            boolean lastIterationFinished = fraction >= ((float) (this.mRepeatCount + 1)) && this.mRepeatCount != -1;
            if (scaledDuration == 0) {
                done = true;
            } else if (newIteration && !lastIterationFinished) {
                notifyListeners(Animator.AnimatorCaller.ON_REPEAT, false);
            } else if (lastIterationFinished) {
                done = true;
            }
            this.mOverallFraction = clampFraction(fraction);
            float currentIterationFraction = getCurrentIterationFraction(this.mOverallFraction, this.mReversing);
            animateValue(currentIterationFraction);
        }
        return done;
    }

    @Override // android.animation.Animator
    void animateValuesInRange(long currentPlayTime, long lastPlayTime) {
        if (currentPlayTime < 0 || lastPlayTime < -1) {
            throw new UnsupportedOperationException("Error: Play time should never be negative.");
        }
        initAnimation();
        long duration = getTotalDuration();
        if (lastPlayTime < 0 || (lastPlayTime == 0 && currentPlayTime > 0)) {
            notifyStartListeners(false);
        } else if (lastPlayTime > duration || (lastPlayTime == duration && currentPlayTime < duration)) {
            notifyStartListeners(true);
        }
        if (duration >= 0) {
            lastPlayTime = Math.min(duration, lastPlayTime);
        }
        long lastPlayTime2 = lastPlayTime - this.mStartDelay;
        long currentPlayTime2 = currentPlayTime - this.mStartDelay;
        if (this.mRepeatCount > 0) {
            int iteration = Math.max(0, (int) (currentPlayTime2 / this.mDuration));
            int lastIteration = Math.max(0, (int) (lastPlayTime2 / this.mDuration));
            if (Math.min(iteration, this.mRepeatCount) != Math.min(lastIteration, this.mRepeatCount)) {
                notifyListeners(Animator.AnimatorCaller.ON_REPEAT, false);
            }
        }
        int iteration2 = this.mRepeatCount;
        if (iteration2 != -1 && currentPlayTime2 > (this.mRepeatCount + 1) * this.mDuration) {
            throw new IllegalStateException("Can't animate a value outside of the duration");
        }
        float fraction = Math.max(0L, currentPlayTime2) / this.mDuration;
        animateValue(getCurrentIterationFraction(fraction, false));
    }

    @Override // android.animation.Animator
    void animateSkipToEnds(long currentPlayTime, long lastPlayTime) {
        boolean doSkip = true;
        boolean inReverse = currentPlayTime < lastPlayTime;
        if (currentPlayTime <= 0 && lastPlayTime > 0) {
            doSkip = true;
        } else {
            long duration = getTotalDuration();
            if (duration < 0 || currentPlayTime < duration || lastPlayTime >= duration) {
                doSkip = false;
            }
        }
        if (doSkip) {
            notifyStartListeners(inReverse);
            skipToEndValue(inReverse);
            notifyEndListeners(inReverse);
        }
    }

    @Override // android.animation.Animator
    void skipToEndValue(boolean inReverse) {
        initAnimation();
        float endFraction = inReverse ? 0.0f : 1.0f;
        if (this.mRepeatCount % 2 == 1 && this.mRepeatMode == 2) {
            endFraction = 0.0f;
        }
        animateValue(endFraction);
    }

    @Override // android.animation.Animator
    boolean isInitialized() {
        return this.mInitialized;
    }

    @Override // android.animation.AnimationHandler.AnimationFrameCallback
    public final boolean doAnimationFrame(long frameTime) {
        long resolveDurationScale;
        if (this.mStartTime < 0) {
            if (this.mReversing) {
                resolveDurationScale = frameTime;
            } else {
                resolveDurationScale = ((long) (this.mStartDelay * resolveDurationScale())) + frameTime;
            }
            this.mStartTime = resolveDurationScale;
        }
        if (this.mPaused) {
            this.mPauseTime = frameTime;
            removeAnimationCallback();
            return false;
        }
        if (this.mResumed) {
            this.mResumed = false;
            if (this.mPauseTime > 0) {
                this.mStartTime += frameTime - this.mPauseTime;
            }
        }
        if (!this.mRunning) {
            if (this.mStartTime > frameTime && this.mSeekFraction == -1.0f) {
                return false;
            }
            this.mRunning = true;
            startAnimation();
        }
        if (this.mLastFrameTime < 0) {
            if (this.mSeekFraction >= 0.0f) {
                long seekTime = (long) (getScaledDuration() * this.mSeekFraction);
                this.mStartTime = frameTime - seekTime;
                this.mSeekFraction = -1.0f;
            }
            this.mStartTimeCommitted = false;
        }
        this.mLastFrameTime = frameTime;
        long currentTime = Math.max(frameTime, this.mStartTime);
        boolean finished = animateBasedOnTime(currentTime);
        if (finished) {
            endAnimation();
        }
        return finished;
    }

    @Override // android.animation.Animator
    boolean pulseAnimationFrame(long frameTime) {
        if (this.mSelfPulse) {
            return false;
        }
        return doAnimationFrame(frameTime);
    }

    private void addOneShotCommitCallback() {
        if (!this.mSelfPulse) {
            return;
        }
        getAnimationHandler().addOneShotCommitCallback(this);
    }

    private void removeAnimationCallback() {
        if (!this.mSelfPulse) {
            return;
        }
        getAnimationHandler().removeCallback(this);
    }

    private void addAnimationCallback(long delay) {
        if (!this.mSelfPulse) {
            return;
        }
        getAnimationHandler().addAnimationFrameCallback(this, delay);
    }

    public float getAnimatedFraction() {
        return this.mCurrentFraction;
    }

    void animateValue(float fraction) {
        if (TRACE_ANIMATION_FRACTION) {
            Trace.traceCounter(8L, getNameForTrace() + hashCode(), (int) (1000.0f * fraction));
        }
        if (this.mValues == null) {
            return;
        }
        float fraction2 = this.mInterpolator.getInterpolation(fraction);
        this.mCurrentFraction = fraction2;
        int numValues = this.mValues.length;
        for (int i = 0; i < numValues; i++) {
            this.mValues[i].calculateValue(fraction2);
        }
        if (this.mSeekFraction >= 0.0f || this.mStartListenersCalled) {
            callOnList(this.mUpdateListeners, Animator.AnimatorCaller.ON_UPDATE, this, false);
        }
    }

    @Override // android.animation.Animator
    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public ValueAnimator mo77clone() {
        ValueAnimator anim = (ValueAnimator) super.mo77clone();
        if (this.mUpdateListeners != null) {
            anim.mUpdateListeners = new ArrayList<>(this.mUpdateListeners);
        }
        anim.mSeekFraction = -1.0f;
        anim.mReversing = false;
        anim.mInitialized = false;
        anim.mStarted = false;
        anim.mRunning = false;
        anim.mPaused = false;
        anim.mResumed = false;
        anim.mStartTime = -1L;
        anim.mStartTimeCommitted = false;
        anim.mAnimationEndRequested = false;
        anim.mPauseTime = -1L;
        anim.mLastFrameTime = -1L;
        anim.mFirstFrameTime = -1L;
        anim.mOverallFraction = 0.0f;
        anim.mCurrentFraction = 0.0f;
        anim.mSelfPulse = true;
        anim.mSuppressSelfPulseRequested = false;
        PropertyValuesHolder[] oldValues = this.mValues;
        if (oldValues != null) {
            int numValues = oldValues.length;
            anim.mValues = new PropertyValuesHolder[numValues];
            anim.mValuesMap = new HashMap<>(numValues);
            for (int i = 0; i < numValues; i++) {
                PropertyValuesHolder newValuesHolder = oldValues[i].mo122clone();
                anim.mValues[i] = newValuesHolder;
                anim.mValuesMap.put(newValuesHolder.getPropertyName(), newValuesHolder);
            }
        }
        return anim;
    }

    public static int getCurrentAnimationsCount() {
        return AnimationHandler.getAnimationCount();
    }

    public String toString() {
        String returnVal = "ValueAnimator@" + Integer.toHexString(hashCode());
        if (this.mValues != null) {
            for (int i = 0; i < this.mValues.length; i++) {
                returnVal = returnVal + "\n    " + this.mValues[i].toString();
            }
        }
        return returnVal;
    }

    @Override // android.animation.Animator
    public void setAllowRunningAsynchronously(boolean mayRunAsync) {
    }

    public AnimationHandler getAnimationHandler() {
        return this.mAnimationHandler != null ? this.mAnimationHandler : AnimationHandler.getInstance();
    }

    public void setAnimationHandler(AnimationHandler animationHandler) {
        this.mAnimationHandler = animationHandler;
    }
}
