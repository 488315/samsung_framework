.class public final synthetic Landroidx/profileinstaller/ProfileInstallerInitializer$$ExternalSyntheticLambda1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic $r8$classId:I

.field public final synthetic f$0:Landroid/content/Context;


# direct methods
.method public synthetic constructor <init>(Landroid/content/Context;I)V
    .locals 0

    .line 1
    iput p2, p0, Landroidx/profileinstaller/ProfileInstallerInitializer$$ExternalSyntheticLambda1;->$r8$classId:I

    .line 2
    .line 3
    iput-object p1, p0, Landroidx/profileinstaller/ProfileInstallerInitializer$$ExternalSyntheticLambda1;->f$0:Landroid/content/Context;

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
    .locals 8

    .line 1
    iget v0, p0, Landroidx/profileinstaller/ProfileInstallerInitializer$$ExternalSyntheticLambda1;->$r8$classId:I

    .line 2
    .line 3
    packed-switch v0, :pswitch_data_0

    .line 4
    .line 5
    .line 6
    goto :goto_0

    .line 7
    :pswitch_0
    iget-object p0, p0, Landroidx/profileinstaller/ProfileInstallerInitializer$$ExternalSyntheticLambda1;->f$0:Landroid/content/Context;

    .line 8
    .line 9
    new-instance v7, Ljava/util/concurrent/ThreadPoolExecutor;

    .line 10
    .line 11
    const/4 v1, 0x0

    .line 12
    const/4 v2, 0x1

    .line 13
    const-wide/16 v3, 0x0

    .line 14
    .line 15
    sget-object v5, Ljava/util/concurrent/TimeUnit;->MILLISECONDS:Ljava/util/concurrent/TimeUnit;

    .line 16
    .line 17
    new-instance v6, Ljava/util/concurrent/LinkedBlockingQueue;

    .line 18
    .line 19
    invoke-direct {v6}, Ljava/util/concurrent/LinkedBlockingQueue;-><init>()V

    .line 20
    .line 21
    .line 22
    move-object v0, v7

    .line 23
    invoke-direct/range {v0 .. v6}, Ljava/util/concurrent/ThreadPoolExecutor;-><init>(IIJLjava/util/concurrent/TimeUnit;Ljava/util/concurrent/BlockingQueue;)V

    .line 24
    .line 25
    .line 26
    new-instance v0, Landroidx/profileinstaller/ProfileInstallerInitializer$$ExternalSyntheticLambda1;

    .line 27
    .line 28
    const/4 v1, 0x1

    .line 29
    invoke-direct {v0, p0, v1}, Landroidx/profileinstaller/ProfileInstallerInitializer$$ExternalSyntheticLambda1;-><init>(Landroid/content/Context;I)V

    .line 30
    .line 31
    .line 32
    invoke-virtual {v7, v0}, Ljava/util/concurrent/ThreadPoolExecutor;->execute(Ljava/lang/Runnable;)V

    .line 33
    .line 34
    .line 35
    return-void

    .line 36
    :goto_0
    iget-object p0, p0, Landroidx/profileinstaller/ProfileInstallerInitializer$$ExternalSyntheticLambda1;->f$0:Landroid/content/Context;

    .line 37
    .line 38
    new-instance v0, Landroidx/profileinstaller/ProfileInstaller$$ExternalSyntheticLambda0;

    .line 39
    .line 40
    invoke-direct {v0}, Landroidx/profileinstaller/ProfileInstaller$$ExternalSyntheticLambda0;-><init>()V

    .line 41
    .line 42
    .line 43
    sget-object v1, Landroidx/profileinstaller/ProfileInstaller;->EMPTY_DIAGNOSTICS:Landroidx/profileinstaller/ProfileInstaller$1;

    .line 44
    .line 45
    const/4 v2, 0x0

    .line 46
    invoke-static {p0, v0, v1, v2}, Landroidx/profileinstaller/ProfileInstaller;->writeProfile(Landroid/content/Context;Ljava/util/concurrent/Executor;Landroidx/profileinstaller/ProfileInstaller$DiagnosticsCallback;Z)V

    .line 47
    .line 48
    .line 49
    return-void

    .line 50
    nop

    .line 51
    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_0
    .end packed-switch
.end method