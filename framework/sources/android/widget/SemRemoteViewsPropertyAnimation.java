package android.widget;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/* loaded from: classes4.dex */
public class SemRemoteViewsPropertyAnimation extends SemRemoteViewsAnimation {
    public static final Parcelable.Creator<SemRemoteViewsPropertyAnimation> CREATOR = new Parcelable.Creator<SemRemoteViewsPropertyAnimation>() { // from class: android.widget.SemRemoteViewsPropertyAnimation.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SemRemoteViewsPropertyAnimation createFromParcel(Parcel in) {
            return new SemRemoteViewsPropertyAnimation(in);
        }

        @Override // android.os.Parcelable.Creator
        public SemRemoteViewsPropertyAnimation[] newArray(int size) {
            return new SemRemoteViewsPropertyAnimation[size];
        }
    };
    private static final String LOG_TAG = "SemRemoteViewsPropertyAnimation";
    private final int mAnimResId;

    public SemRemoteViewsPropertyAnimation(int viewId, int animResId) {
        super(viewId);
        this.mAnimResId = animResId;
    }

    protected SemRemoteViewsPropertyAnimation(Parcel parcel) {
        super(parcel);
        this.mAnimResId = parcel.readInt();
    }

    /* renamed from: android.widget.SemRemoteViewsPropertyAnimation$1 */
    /* loaded from: classes4.dex */
    class AnonymousClass1 implements Parcelable.Creator<SemRemoteViewsPropertyAnimation> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SemRemoteViewsPropertyAnimation createFromParcel(Parcel in) {
            return new SemRemoteViewsPropertyAnimation(in);
        }

        @Override // android.os.Parcelable.Creator
        public SemRemoteViewsPropertyAnimation[] newArray(int size) {
            return new SemRemoteViewsPropertyAnimation[size];
        }
    }

    @Override // android.widget.SemRemoteViewsAnimation, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.mAnimResId);
    }

    public static void writeToParcel(SemRemoteViewsPropertyAnimation c, Parcel out) {
        if (c != null) {
            c.writeToParcel(out, 0);
        } else {
            out.writeString(null);
        }
    }

    @Override // android.widget.SemRemoteViewsAnimation, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.widget.SemRemoteViewsAnimation
    protected void startAnimation(View root) {
        View target;
        if (root == null || this.mAnimResId <= 0 || (target = root.findViewById(this.mViewId)) == null) {
            return;
        }
        Context context = root.getContext();
        AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(context, this.mAnimResId);
        if (animatorSet == null) {
            return;
        }
        animatorSet.setTarget(target);
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: android.widget.SemRemoteViewsPropertyAnimation.2
            AnonymousClass2() {
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                SemRemoteViewsPropertyAnimation.this.mIsExpired = true;
            }
        });
        if (this.mIsExpired) {
            animatorSet.setDuration(0L);
        }
        animatorSet.start();
    }

    /* renamed from: android.widget.SemRemoteViewsPropertyAnimation$2 */
    /* loaded from: classes4.dex */
    class AnonymousClass2 extends AnimatorListenerAdapter {
        AnonymousClass2() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animation) {
            SemRemoteViewsPropertyAnimation.this.mIsExpired = true;
        }
    }

    @Override // android.widget.SemRemoteViewsAnimation
    /* renamed from: endAnimation */
    public void lambda$play$0(View root) {
    }
}
