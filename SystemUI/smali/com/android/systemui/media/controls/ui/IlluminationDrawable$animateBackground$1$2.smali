.class public final Lcom/android/systemui/media/controls/ui/IlluminationDrawable$animateBackground$1$2;
.super Landroid/animation/AnimatorListenerAdapter;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final synthetic this$0:Lcom/android/systemui/media/controls/ui/IlluminationDrawable;


# direct methods
.method public constructor <init>(Lcom/android/systemui/media/controls/ui/IlluminationDrawable;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/media/controls/ui/IlluminationDrawable$animateBackground$1$2;->this$0:Lcom/android/systemui/media/controls/ui/IlluminationDrawable;

    .line 2
    .line 3
    invoke-direct {p0}, Landroid/animation/AnimatorListenerAdapter;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final onAnimationEnd(Landroid/animation/Animator;)V
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/android/systemui/media/controls/ui/IlluminationDrawable$animateBackground$1$2;->this$0:Lcom/android/systemui/media/controls/ui/IlluminationDrawable;

    .line 2
    .line 3
    const/4 p1, 0x0

    .line 4
    invoke-static {p0, p1}, Lcom/android/systemui/media/controls/ui/IlluminationDrawable;->access$setBackgroundAnimation$p(Lcom/android/systemui/media/controls/ui/IlluminationDrawable;Landroid/animation/ValueAnimator;)V

    .line 5
    .line 6
    .line 7
    return-void
.end method