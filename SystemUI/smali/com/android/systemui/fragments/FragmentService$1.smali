.class public final Lcom/android/systemui/fragments/FragmentService$1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/android/systemui/statusbar/policy/ConfigurationController$ConfigurationListener;


# instance fields
.field public final synthetic this$0:Lcom/android/systemui/fragments/FragmentService;


# direct methods
.method public constructor <init>(Lcom/android/systemui/fragments/FragmentService;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/fragments/FragmentService$1;->this$0:Lcom/android/systemui/fragments/FragmentService;

    .line 2
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final onConfigChanged(Landroid/content/res/Configuration;)V
    .locals 3

    .line 1
    iget-object p0, p0, Lcom/android/systemui/fragments/FragmentService$1;->this$0:Lcom/android/systemui/fragments/FragmentService;

    .line 2
    .line 3
    iget-object p0, p0, Lcom/android/systemui/fragments/FragmentService;->mHosts:Landroid/util/ArrayMap;

    .line 4
    .line 5
    invoke-virtual {p0}, Landroid/util/ArrayMap;->values()Ljava/util/Collection;

    .line 6
    .line 7
    .line 8
    move-result-object p0

    .line 9
    invoke-interface {p0}, Ljava/util/Collection;->iterator()Ljava/util/Iterator;

    .line 10
    .line 11
    .line 12
    move-result-object p0

    .line 13
    :goto_0
    invoke-interface {p0}, Ljava/util/Iterator;->hasNext()Z

    .line 14
    .line 15
    .line 16
    move-result v0

    .line 17
    if-eqz v0, :cond_0

    .line 18
    .line 19
    invoke-interface {p0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    .line 20
    .line 21
    .line 22
    move-result-object v0

    .line 23
    check-cast v0, Lcom/android/systemui/fragments/FragmentService$FragmentHostState;

    .line 24
    .line 25
    iget-object v1, v0, Lcom/android/systemui/fragments/FragmentService$FragmentHostState;->this$0:Lcom/android/systemui/fragments/FragmentService;

    .line 26
    .line 27
    iget-object v1, v1, Lcom/android/systemui/fragments/FragmentService;->mHandler:Landroid/os/Handler;

    .line 28
    .line 29
    new-instance v2, Lcom/android/systemui/fragments/FragmentService$FragmentHostState$$ExternalSyntheticLambda0;

    .line 30
    .line 31
    invoke-direct {v2, v0, p1}, Lcom/android/systemui/fragments/FragmentService$FragmentHostState$$ExternalSyntheticLambda0;-><init>(Lcom/android/systemui/fragments/FragmentService$FragmentHostState;Landroid/content/res/Configuration;)V

    .line 32
    .line 33
    .line 34
    invoke-virtual {v1, v2}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    .line 35
    .line 36
    .line 37
    goto :goto_0

    .line 38
    :cond_0
    return-void
.end method
