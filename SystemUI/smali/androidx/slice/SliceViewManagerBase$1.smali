.class public final Landroidx/slice/SliceViewManagerBase$1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/util/concurrent/Executor;


# instance fields
.field public final synthetic val$h:Landroid/os/Handler;


# direct methods
.method public constructor <init>(Landroidx/slice/SliceViewManagerBase;Landroid/os/Handler;)V
    .locals 0

    .line 1
    iput-object p2, p0, Landroidx/slice/SliceViewManagerBase$1;->val$h:Landroid/os/Handler;

    .line 2
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final execute(Ljava/lang/Runnable;)V
    .locals 0

    .line 1
    iget-object p0, p0, Landroidx/slice/SliceViewManagerBase$1;->val$h:Landroid/os/Handler;

    .line 2
    .line 3
    invoke-virtual {p0, p1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    .line 4
    .line 5
    .line 6
    return-void
.end method