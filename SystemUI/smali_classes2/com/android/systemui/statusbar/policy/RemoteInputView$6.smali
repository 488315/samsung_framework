.class public final Lcom/android/systemui/statusbar/policy/RemoteInputView$6;
.super Landroidx/core/animation/AnimatorListenerAdapter;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final synthetic this$0:Lcom/android/systemui/statusbar/policy/RemoteInputView;


# direct methods
.method public constructor <init>(Lcom/android/systemui/statusbar/policy/RemoteInputView;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/statusbar/policy/RemoteInputView$6;->this$0:Lcom/android/systemui/statusbar/policy/RemoteInputView;

    .line 2
    .line 3
    invoke-direct {p0}, Landroidx/core/animation/AnimatorListenerAdapter;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final onAnimationEnd(Landroidx/core/animation/Animator;)V
    .locals 0

    .line 1
    sget-object p1, Lcom/android/systemui/statusbar/policy/RemoteInputView;->VIEW_TAG:Ljava/lang/Object;

    .line 2
    .line 3
    iget-object p0, p0, Lcom/android/systemui/statusbar/policy/RemoteInputView$6;->this$0:Lcom/android/systemui/statusbar/policy/RemoteInputView;

    .line 4
    .line 5
    const/high16 p1, 0x3f800000    # 1.0f

    .line 6
    .line 7
    invoke-virtual {p0, p1}, Lcom/android/systemui/statusbar/policy/RemoteInputView;->setFocusAnimationScaleY(F)V

    .line 8
    .line 9
    .line 10
    return-void
.end method