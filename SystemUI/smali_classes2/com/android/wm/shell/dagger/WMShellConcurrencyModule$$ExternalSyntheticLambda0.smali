.class public final synthetic Lcom/android/wm/shell/dagger/WMShellConcurrencyModule$$ExternalSyntheticLambda0;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic f$0:[Landroid/view/Choreographer;


# direct methods
.method public synthetic constructor <init>([Landroid/view/Choreographer;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/wm/shell/dagger/WMShellConcurrencyModule$$ExternalSyntheticLambda0;->f$0:[Landroid/view/Choreographer;

    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final run()V
    .locals 2

    .line 1
    iget-object p0, p0, Lcom/android/wm/shell/dagger/WMShellConcurrencyModule$$ExternalSyntheticLambda0;->f$0:[Landroid/view/Choreographer;

    .line 2
    .line 3
    const/4 v0, 0x0

    .line 4
    invoke-static {}, Landroid/view/Choreographer;->getInstance()Landroid/view/Choreographer;

    .line 5
    .line 6
    .line 7
    move-result-object v1

    .line 8
    aput-object v1, p0, v0

    .line 9
    .line 10
    return-void
.end method