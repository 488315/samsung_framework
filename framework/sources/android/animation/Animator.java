package android.animation;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.res.ConstantState;
import android.util.LongArray;
import java.util.ArrayList;

/* loaded from: classes.dex */
public abstract class Animator implements Cloneable {
    public static final long DURATION_INFINITE = -1;
    private static long sBackgroundPauseDelay = 1000;
    private Object[] mCachedList;
    private AnimatorConstantState mConstantState;
    ArrayList<AnimatorListener> mListeners = null;
    ArrayList<AnimatorPauseListener> mPauseListeners = null;
    boolean mPaused = false;
    int mChangingConfigurations = 0;
    boolean mStartListenersCalled = false;

    /* loaded from: classes.dex */
    public interface AnimatorPauseListener {
        void onAnimationPause(Animator animator);

        void onAnimationResume(Animator animator);
    }

    public abstract long getDuration();

    public abstract long getStartDelay();

    public abstract boolean isRunning();

    public abstract Animator setDuration(long j);

    public abstract void setInterpolator(TimeInterpolator timeInterpolator);

    public abstract void setStartDelay(long j);

    public static void setBackgroundPauseDelay(long value) {
        sBackgroundPauseDelay = value;
    }

    public static long getBackgroundPauseDelay() {
        return sBackgroundPauseDelay;
    }

    public static void setAnimatorPausingEnabled(boolean enable) {
        AnimationHandler.setAnimatorPausingEnabled(enable);
        AnimationHandler.setOverrideAnimatorPausingSystemProperty(!enable);
    }

    public void start() {
    }

    public void cancel() {
    }

    public void end() {
    }

    public void pause() {
        if ((isStarted() || this.mStartListenersCalled) && !this.mPaused) {
            this.mPaused = true;
            notifyPauseListeners(AnimatorCaller.ON_PAUSE);
        }
    }

    public void resume() {
        if (this.mPaused) {
            this.mPaused = false;
            notifyPauseListeners(AnimatorCaller.ON_RESUME);
        }
    }

    public boolean isPaused() {
        return this.mPaused;
    }

    public long getTotalDuration() {
        long duration = getDuration();
        if (duration == -1) {
            return -1L;
        }
        return getStartDelay() + duration;
    }

    public TimeInterpolator getInterpolator() {
        return null;
    }

    public boolean isStarted() {
        return isRunning();
    }

    public void addListener(AnimatorListener listener) {
        if (this.mListeners == null) {
            this.mListeners = new ArrayList<>();
        }
        this.mListeners.add(listener);
    }

    public void removeListener(AnimatorListener listener) {
        ArrayList<AnimatorListener> arrayList = this.mListeners;
        if (arrayList == null) {
            return;
        }
        arrayList.remove(listener);
        if (this.mListeners.size() == 0) {
            this.mListeners = null;
        }
    }

    public ArrayList<AnimatorListener> getListeners() {
        return this.mListeners;
    }

    public void addPauseListener(AnimatorPauseListener listener) {
        if (this.mPauseListeners == null) {
            this.mPauseListeners = new ArrayList<>();
        }
        this.mPauseListeners.add(listener);
    }

    public void removePauseListener(AnimatorPauseListener listener) {
        ArrayList<AnimatorPauseListener> arrayList = this.mPauseListeners;
        if (arrayList == null) {
            return;
        }
        arrayList.remove(listener);
        if (this.mPauseListeners.size() == 0) {
            this.mPauseListeners = null;
        }
    }

    public void removeAllListeners() {
        ArrayList<AnimatorListener> arrayList = this.mListeners;
        if (arrayList != null) {
            arrayList.clear();
            this.mListeners = null;
        }
        ArrayList<AnimatorPauseListener> arrayList2 = this.mPauseListeners;
        if (arrayList2 != null) {
            arrayList2.clear();
            this.mPauseListeners = null;
        }
    }

    public int getChangingConfigurations() {
        return this.mChangingConfigurations;
    }

    public void setChangingConfigurations(int configs) {
        this.mChangingConfigurations = configs;
    }

    public void appendChangingConfigurations(int configs) {
        this.mChangingConfigurations |= configs;
    }

    public ConstantState<Animator> createConstantState() {
        return new AnimatorConstantState(this);
    }

    @Override // 
    /* renamed from: clone */
    public Animator mo57clone() {
        try {
            Animator anim = (Animator) super.clone();
            if (this.mListeners != null) {
                anim.mListeners = new ArrayList<>(this.mListeners);
            }
            if (this.mPauseListeners != null) {
                anim.mPauseListeners = new ArrayList<>(this.mPauseListeners);
            }
            anim.mCachedList = null;
            anim.mStartListenersCalled = false;
            return anim;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public void setupStartValues() {
    }

    public void setupEndValues() {
    }

    public void setTarget(Object target) {
    }

    public boolean canReverse() {
        return false;
    }

    public void reverse() {
        throw new IllegalStateException("Reverse is not supported");
    }

    public boolean pulseAnimationFrame(long frameTime) {
        return false;
    }

    public void startWithoutPulsing(boolean inReverse) {
        if (inReverse) {
            reverse();
        } else {
            start();
        }
    }

    public void skipToEndValue(boolean inReverse) {
    }

    public boolean isInitialized() {
        return true;
    }

    public void animateValuesInRange(long currentPlayTime, long lastPlayTime) {
    }

    public void animateSkipToEnds(long currentPlayTime, long lastPlayTime) {
    }

    public void getStartAndEndTimes(LongArray times, long offset) {
        long startTime = getStartDelay() + offset;
        if (times.indexOf(startTime) < 0) {
            times.add(startTime);
        }
        long duration = getTotalDuration();
        if (duration != -1) {
            long endTime = duration + offset;
            if (times.indexOf(endTime) < 0) {
                times.add(endTime);
            }
        }
    }

    public void notifyListeners(AnimatorCaller<AnimatorListener, Animator> notification, boolean isReverse) {
        callOnList(this.mListeners, notification, this, isReverse);
    }

    void notifyPauseListeners(AnimatorCaller<AnimatorPauseListener, Animator> notification) {
        callOnList(this.mPauseListeners, notification, this, false);
    }

    public void notifyStartListeners(boolean isReversing) {
        boolean startListenersCalled = this.mStartListenersCalled;
        this.mStartListenersCalled = true;
        if (this.mListeners != null && !startListenersCalled) {
            notifyListeners(AnimatorCaller.ON_START, isReversing);
        }
    }

    public void notifyEndListeners(boolean isReversing) {
        boolean startListenersCalled = this.mStartListenersCalled;
        this.mStartListenersCalled = false;
        if (this.mListeners != null && startListenersCalled) {
            notifyListeners(AnimatorCaller.ON_END, isReversing);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T, A> void callOnList(ArrayList<T> list, AnimatorCaller<T, A> animatorCaller, A animator, boolean isReverse) {
        Object[] array;
        int size = list == null ? 0 : list.size();
        if (size > 0) {
            Object[] objArr = this.mCachedList;
            if (objArr == null || objArr.length < size) {
                array = new Object[size];
            } else {
                array = this.mCachedList;
                this.mCachedList = null;
            }
            list.toArray(array);
            for (int i = 0; i < size; i++) {
                animatorCaller.call(array[i], animator, isReverse);
                array[i] = null;
            }
            this.mCachedList = array;
        }
    }

    /* loaded from: classes.dex */
    public interface AnimatorListener {
        void onAnimationCancel(Animator animator);

        void onAnimationEnd(Animator animator);

        void onAnimationRepeat(Animator animator);

        void onAnimationStart(Animator animator);

        default void onAnimationStart(Animator animation, boolean isReverse) {
            onAnimationStart(animation);
        }

        default void onAnimationEnd(Animator animation, boolean isReverse) {
            onAnimationEnd(animation);
        }
    }

    public void setAllowRunningAsynchronously(boolean mayRunAsync) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class AnimatorConstantState extends ConstantState<Animator> {
        final Animator mAnimator;
        int mChangingConf;

        public AnimatorConstantState(Animator animator) {
            this.mAnimator = animator;
            animator.mConstantState = this;
            this.mChangingConf = animator.getChangingConfigurations();
        }

        @Override // android.content.res.ConstantState
        public int getChangingConfigurations() {
            return this.mChangingConf;
        }

        @Override // android.content.res.ConstantState
        /* renamed from: newInstance */
        public Animator newInstance2() {
            Animator clone = this.mAnimator.mo57clone();
            clone.mConstantState = this;
            return clone;
        }
    }

    /* loaded from: classes.dex */
    public interface AnimatorCaller<T, A> {
        public static final AnimatorCaller<AnimatorListener, Animator> ON_START = new AnimatorCaller() { // from class: android.animation.Animator$AnimatorCaller$$ExternalSyntheticLambda0
            @Override // android.animation.Animator.AnimatorCaller
            public final void call(Object obj, Object obj2, boolean z) {
                ((Animator.AnimatorListener) obj).onAnimationStart((Animator) obj2, z);
            }
        };
        public static final AnimatorCaller<AnimatorListener, Animator> ON_END = new AnimatorCaller() { // from class: android.animation.Animator$AnimatorCaller$$ExternalSyntheticLambda1
            @Override // android.animation.Animator.AnimatorCaller
            public final void call(Object obj, Object obj2, boolean z) {
                ((Animator.AnimatorListener) obj).onAnimationEnd((Animator) obj2, z);
            }
        };
        public static final AnimatorCaller<AnimatorListener, Animator> ON_CANCEL = new AnimatorCaller() { // from class: android.animation.Animator$AnimatorCaller$$ExternalSyntheticLambda2
            @Override // android.animation.Animator.AnimatorCaller
            public final void call(Object obj, Object obj2, boolean z) {
                ((Animator.AnimatorListener) obj).onAnimationCancel((Animator) obj2);
            }
        };
        public static final AnimatorCaller<AnimatorListener, Animator> ON_REPEAT = new AnimatorCaller() { // from class: android.animation.Animator$AnimatorCaller$$ExternalSyntheticLambda3
            @Override // android.animation.Animator.AnimatorCaller
            public final void call(Object obj, Object obj2, boolean z) {
                ((Animator.AnimatorListener) obj).onAnimationRepeat((Animator) obj2);
            }
        };
        public static final AnimatorCaller<AnimatorPauseListener, Animator> ON_PAUSE = new AnimatorCaller() { // from class: android.animation.Animator$AnimatorCaller$$ExternalSyntheticLambda4
            @Override // android.animation.Animator.AnimatorCaller
            public final void call(Object obj, Object obj2, boolean z) {
                ((Animator.AnimatorPauseListener) obj).onAnimationPause((Animator) obj2);
            }
        };
        public static final AnimatorCaller<AnimatorPauseListener, Animator> ON_RESUME = new AnimatorCaller() { // from class: android.animation.Animator$AnimatorCaller$$ExternalSyntheticLambda5
            @Override // android.animation.Animator.AnimatorCaller
            public final void call(Object obj, Object obj2, boolean z) {
                ((Animator.AnimatorPauseListener) obj).onAnimationResume((Animator) obj2);
            }
        };
        public static final AnimatorCaller<ValueAnimator.AnimatorUpdateListener, ValueAnimator> ON_UPDATE = new AnimatorCaller() { // from class: android.animation.Animator$AnimatorCaller$$ExternalSyntheticLambda6
            @Override // android.animation.Animator.AnimatorCaller
            public final void call(Object obj, Object obj2, boolean z) {
                ((ValueAnimator.AnimatorUpdateListener) obj).onAnimationUpdate((ValueAnimator) obj2);
            }
        };

        void call(T t, A a, boolean z);
    }
}
