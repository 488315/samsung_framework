.class public final Lcom/android/systemui/util/AsyncActivityLauncher$startActivityAsUser$1$1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic $waitResult:Landroid/app/WaitResult;

.field public final synthetic this$0:Lcom/android/systemui/util/AsyncActivityLauncher;


# direct methods
.method public constructor <init>(Lcom/android/systemui/util/AsyncActivityLauncher;Landroid/app/WaitResult;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/util/AsyncActivityLauncher$startActivityAsUser$1$1;->this$0:Lcom/android/systemui/util/AsyncActivityLauncher;

    .line 2
    .line 3
    iput-object p2, p0, Lcom/android/systemui/util/AsyncActivityLauncher$startActivityAsUser$1$1;->$waitResult:Landroid/app/WaitResult;

    .line 4
    .line 5
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 6
    .line 7
    .line 8
    return-void
.end method


# virtual methods
.method public final run()V
    .locals 1

    .line 1
    iget-object v0, p0, Lcom/android/systemui/util/AsyncActivityLauncher$startActivityAsUser$1$1;->this$0:Lcom/android/systemui/util/AsyncActivityLauncher;

    .line 2
    .line 3
    iget-object v0, v0, Lcom/android/systemui/util/AsyncActivityLauncher;->pendingCallback:Lkotlin/jvm/functions/Function1;

    .line 4
    .line 5
    if-eqz v0, :cond_0

    .line 6
    .line 7
    iget-object p0, p0, Lcom/android/systemui/util/AsyncActivityLauncher$startActivityAsUser$1$1;->$waitResult:Landroid/app/WaitResult;

    .line 8
    .line 9
    invoke-interface {v0, p0}, Lkotlin/jvm/functions/Function1;->invoke(Ljava/lang/Object;)Ljava/lang/Object;

    .line 10
    .line 11
    .line 12
    :cond_0
    return-void
.end method
