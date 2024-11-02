package android.widget;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/* loaded from: classes4.dex */
public class SemRemoteViewsViewAnimation extends SemRemoteViewsAnimation {
    public static final Parcelable.Creator<SemRemoteViewsViewAnimation> CREATOR = new Parcelable.Creator<SemRemoteViewsViewAnimation>() { // from class: android.widget.SemRemoteViewsViewAnimation.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SemRemoteViewsViewAnimation createFromParcel(Parcel in) {
            return new SemRemoteViewsViewAnimation(in);
        }

        @Override // android.os.Parcelable.Creator
        public SemRemoteViewsViewAnimation[] newArray(int size) {
            return new SemRemoteViewsViewAnimation[size];
        }
    };
    private static final String LOG_TAG = "SemRemoteViewsViewAnimation";
    private final int mAnimResId;

    public SemRemoteViewsViewAnimation(int viewId, int animResId) {
        super(viewId);
        this.mAnimResId = animResId;
    }

    protected SemRemoteViewsViewAnimation(Parcel parcel) {
        super(parcel);
        this.mAnimResId = parcel.readInt();
    }

    /* renamed from: android.widget.SemRemoteViewsViewAnimation$1 */
    /* loaded from: classes4.dex */
    class AnonymousClass1 implements Parcelable.Creator<SemRemoteViewsViewAnimation> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SemRemoteViewsViewAnimation createFromParcel(Parcel in) {
            return new SemRemoteViewsViewAnimation(in);
        }

        @Override // android.os.Parcelable.Creator
        public SemRemoteViewsViewAnimation[] newArray(int size) {
            return new SemRemoteViewsViewAnimation[size];
        }
    }

    @Override // android.widget.SemRemoteViewsAnimation, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.mAnimResId);
    }

    public static void writeToParcel(SemRemoteViewsViewAnimation c, Parcel out) {
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
        if (root == null || this.mAnimResId <= 0 || this.mIsExpired || (target = root.findViewById(this.mViewId)) == null) {
            return;
        }
        Context context = root.getContext();
        Animation anim = AnimationUtils.loadAnimation(context, this.mAnimResId);
        if (anim == null) {
            return;
        }
        anim.setAnimationListener(new Animation.AnimationListener() { // from class: android.widget.SemRemoteViewsViewAnimation.2
            AnonymousClass2() {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                SemRemoteViewsViewAnimation.this.mIsExpired = true;
            }
        });
        if (this.mIsExpired) {
            anim.setDuration(0L);
        }
        target.startAnimation(anim);
    }

    /* renamed from: android.widget.SemRemoteViewsViewAnimation$2 */
    /* loaded from: classes4.dex */
    class AnonymousClass2 implements Animation.AnimationListener {
        AnonymousClass2() {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationStart(Animation animation) {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationRepeat(Animation animation) {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationEnd(Animation animation) {
            SemRemoteViewsViewAnimation.this.mIsExpired = true;
        }
    }

    @Override // android.widget.SemRemoteViewsAnimation
    /* renamed from: endAnimation */
    public void lambda$play$0(View root) {
    }
}
