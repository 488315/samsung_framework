.class public final synthetic Lcom/android/wm/shell/keyguard/KeyguardTransitionHandler$KeyguardTransitionsImpl$$ExternalSyntheticLambda0;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic f$0:Lcom/android/wm/shell/keyguard/KeyguardTransitionHandler$KeyguardTransitionsImpl;

.field public final synthetic f$1:Landroid/window/IRemoteTransition;

.field public final synthetic f$2:Landroid/window/IRemoteTransition;

.field public final synthetic f$3:Landroid/window/IRemoteTransition;

.field public final synthetic f$4:Landroid/window/IRemoteTransition;


# direct methods
.method public synthetic constructor <init>(Lcom/android/wm/shell/keyguard/KeyguardTransitionHandler$KeyguardTransitionsImpl;Lcom/android/systemui/keyguard/KeyguardService$1;Lcom/android/systemui/keyguard/KeyguardService$1;Lcom/android/systemui/keyguard/KeyguardService$1;Lcom/android/systemui/keyguard/KeyguardService$1;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/wm/shell/keyguard/KeyguardTransitionHandler$KeyguardTransitionsImpl$$ExternalSyntheticLambda0;->f$0:Lcom/android/wm/shell/keyguard/KeyguardTransitionHandler$KeyguardTransitionsImpl;

    .line 5
    .line 6
    iput-object p2, p0, Lcom/android/wm/shell/keyguard/KeyguardTransitionHandler$KeyguardTransitionsImpl$$ExternalSyntheticLambda0;->f$1:Landroid/window/IRemoteTransition;

    .line 7
    .line 8
    iput-object p3, p0, Lcom/android/wm/shell/keyguard/KeyguardTransitionHandler$KeyguardTransitionsImpl$$ExternalSyntheticLambda0;->f$2:Landroid/window/IRemoteTransition;

    .line 9
    .line 10
    iput-object p4, p0, Lcom/android/wm/shell/keyguard/KeyguardTransitionHandler$KeyguardTransitionsImpl$$ExternalSyntheticLambda0;->f$3:Landroid/window/IRemoteTransition;

    .line 11
    .line 12
    iput-object p5, p0, Lcom/android/wm/shell/keyguard/KeyguardTransitionHandler$KeyguardTransitionsImpl$$ExternalSyntheticLambda0;->f$4:Landroid/window/IRemoteTransition;

    .line 13
    .line 14
    return-void
.end method


# virtual methods
.method public final run()V
    .locals 4

    .line 1
    iget-object v0, p0, Lcom/android/wm/shell/keyguard/KeyguardTransitionHandler$KeyguardTransitionsImpl$$ExternalSyntheticLambda0;->f$0:Lcom/android/wm/shell/keyguard/KeyguardTransitionHandler$KeyguardTransitionsImpl;

    .line 2
    .line 3
    iget-object v1, p0, Lcom/android/wm/shell/keyguard/KeyguardTransitionHandler$KeyguardTransitionsImpl$$ExternalSyntheticLambda0;->f$1:Landroid/window/IRemoteTransition;

    .line 4
    .line 5
    iget-object v2, p0, Lcom/android/wm/shell/keyguard/KeyguardTransitionHandler$KeyguardTransitionsImpl$$ExternalSyntheticLambda0;->f$2:Landroid/window/IRemoteTransition;

    .line 6
    .line 7
    iget-object v3, p0, Lcom/android/wm/shell/keyguard/KeyguardTransitionHandler$KeyguardTransitionsImpl$$ExternalSyntheticLambda0;->f$3:Landroid/window/IRemoteTransition;

    .line 8
    .line 9
    iget-object p0, p0, Lcom/android/wm/shell/keyguard/KeyguardTransitionHandler$KeyguardTransitionsImpl$$ExternalSyntheticLambda0;->f$4:Landroid/window/IRemoteTransition;

    .line 10
    .line 11
    iget-object v0, v0, Lcom/android/wm/shell/keyguard/KeyguardTransitionHandler$KeyguardTransitionsImpl;->this$0:Lcom/android/wm/shell/keyguard/KeyguardTransitionHandler;

    .line 12
    .line 13
    iput-object v1, v0, Lcom/android/wm/shell/keyguard/KeyguardTransitionHandler;->mExitTransition:Landroid/window/IRemoteTransition;

    .line 14
    .line 15
    iput-object v2, v0, Lcom/android/wm/shell/keyguard/KeyguardTransitionHandler;->mOccludeTransition:Landroid/window/IRemoteTransition;

    .line 16
    .line 17
    iput-object v3, v0, Lcom/android/wm/shell/keyguard/KeyguardTransitionHandler;->mOccludeByDreamTransition:Landroid/window/IRemoteTransition;

    .line 18
    .line 19
    iput-object p0, v0, Lcom/android/wm/shell/keyguard/KeyguardTransitionHandler;->mUnoccludeTransition:Landroid/window/IRemoteTransition;

    .line 20
    .line 21
    return-void
.end method
