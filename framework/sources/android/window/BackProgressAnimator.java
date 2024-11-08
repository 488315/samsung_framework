package android.window;

import android.util.FloatProperty;
import com.android.internal.dynamicanimation.animation.DynamicAnimation;
import com.android.internal.dynamicanimation.animation.SpringAnimation;
import com.android.internal.dynamicanimation.animation.SpringForce;

/* loaded from: classes4.dex */
public class BackProgressAnimator {
    private static final FloatProperty<BackProgressAnimator> PROGRESS_PROP = new FloatProperty<BackProgressAnimator>("progress") { // from class: android.window.BackProgressAnimator.1
        AnonymousClass1(String name) {
            super(name);
        }

        @Override // android.util.FloatProperty
        public void setValue(BackProgressAnimator animator, float value) {
            animator.setProgress(value);
            animator.updateProgressValue(value);
        }

        @Override // android.util.Property
        public Float get(BackProgressAnimator object) {
            return Float.valueOf(object.getProgress());
        }
    };
    private static final float SCALE_FACTOR = 100.0f;
    private ProgressCallback mCallback;
    private BackMotionEvent mLastBackEvent;
    private final SpringAnimation mSpring;
    private float mProgress = 0.0f;
    private boolean mBackAnimationInProgress = false;

    /* loaded from: classes4.dex */
    public interface ProgressCallback {
        void onProgressUpdate(BackEvent backEvent);
    }

    public void setProgress(float progress) {
        this.mProgress = progress;
    }

    public float getProgress() {
        return this.mProgress;
    }

    /* renamed from: android.window.BackProgressAnimator$1 */
    /* loaded from: classes4.dex */
    class AnonymousClass1 extends FloatProperty<BackProgressAnimator> {
        AnonymousClass1(String name) {
            super(name);
        }

        @Override // android.util.FloatProperty
        public void setValue(BackProgressAnimator animator, float value) {
            animator.setProgress(value);
            animator.updateProgressValue(value);
        }

        @Override // android.util.Property
        public Float get(BackProgressAnimator object) {
            return Float.valueOf(object.getProgress());
        }
    }

    public BackProgressAnimator() {
        SpringAnimation springAnimation = new SpringAnimation(this, PROGRESS_PROP);
        this.mSpring = springAnimation;
        springAnimation.setSpring(new SpringForce().setStiffness(1500.0f).setDampingRatio(1.0f));
    }

    public void onBackProgressed(BackMotionEvent event) {
        if (!this.mBackAnimationInProgress) {
            return;
        }
        this.mLastBackEvent = event;
        SpringAnimation springAnimation = this.mSpring;
        if (springAnimation == null) {
            return;
        }
        springAnimation.animateToFinalPosition(event.getProgress() * 100.0f);
    }

    public void onBackStarted(BackMotionEvent event, ProgressCallback callback) {
        reset();
        this.mLastBackEvent = event;
        this.mCallback = callback;
        this.mBackAnimationInProgress = true;
    }

    public void reset() {
        this.mSpring.animateToFinalPosition(0.0f);
        if (this.mSpring.canSkipToEnd()) {
            this.mSpring.skipToEnd();
        } else {
            this.mSpring.cancel();
        }
        this.mBackAnimationInProgress = false;
        this.mLastBackEvent = null;
        this.mCallback = null;
        this.mProgress = 0.0f;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: android.window.BackProgressAnimator$2 */
    /* loaded from: classes4.dex */
    public class AnonymousClass2 implements DynamicAnimation.OnAnimationEndListener {
        final /* synthetic */ Runnable val$finishCallback;

        AnonymousClass2(Runnable runnable) {
            finishCallback = runnable;
        }

        @Override // com.android.internal.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
        public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {
            BackProgressAnimator.this.mSpring.removeEndListener(this);
            finishCallback.run();
            BackProgressAnimator.this.reset();
        }
    }

    public void onBackCancelled(Runnable finishCallback) {
        DynamicAnimation.OnAnimationEndListener listener = new DynamicAnimation.OnAnimationEndListener() { // from class: android.window.BackProgressAnimator.2
            final /* synthetic */ Runnable val$finishCallback;

            AnonymousClass2(Runnable finishCallback2) {
                finishCallback = finishCallback2;
            }

            @Override // com.android.internal.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
            public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {
                BackProgressAnimator.this.mSpring.removeEndListener(this);
                finishCallback.run();
                BackProgressAnimator.this.reset();
            }
        };
        this.mSpring.addEndListener(listener);
        this.mSpring.animateToFinalPosition(0.0f);
    }

    public boolean isBackAnimationInProgress() {
        return this.mBackAnimationInProgress;
    }

    public void updateProgressValue(float progress) {
        ProgressCallback progressCallback;
        BackMotionEvent backMotionEvent = this.mLastBackEvent;
        if (backMotionEvent == null || (progressCallback = this.mCallback) == null || !this.mBackAnimationInProgress) {
            return;
        }
        progressCallback.onProgressUpdate(new BackEvent(backMotionEvent.getTouchX(), this.mLastBackEvent.getTouchY(), progress / 100.0f, this.mLastBackEvent.getSwipeEdge()));
    }
}
