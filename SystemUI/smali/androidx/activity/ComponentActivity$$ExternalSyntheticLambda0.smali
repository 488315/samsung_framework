.class public final synthetic Landroidx/activity/ComponentActivity$$ExternalSyntheticLambda0;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic $r8$classId:I

.field public final synthetic f$0:Ljava/lang/Object;


# direct methods
.method public synthetic constructor <init>(Ljava/lang/Object;I)V
    .locals 0

    .line 1
    iput p2, p0, Landroidx/activity/ComponentActivity$$ExternalSyntheticLambda0;->$r8$classId:I

    .line 2
    .line 3
    iput-object p1, p0, Landroidx/activity/ComponentActivity$$ExternalSyntheticLambda0;->f$0:Ljava/lang/Object;

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
    iget v0, p0, Landroidx/activity/ComponentActivity$$ExternalSyntheticLambda0;->$r8$classId:I

    .line 2
    .line 3
    packed-switch v0, :pswitch_data_0

    .line 4
    .line 5
    .line 6
    goto :goto_0

    .line 7
    :pswitch_0
    iget-object p0, p0, Landroidx/activity/ComponentActivity$$ExternalSyntheticLambda0;->f$0:Ljava/lang/Object;

    .line 8
    .line 9
    check-cast p0, Landroidx/activity/ComponentActivity;

    .line 10
    .line 11
    invoke-virtual {p0}, Landroid/app/Activity;->invalidateOptionsMenu()V

    .line 12
    .line 13
    .line 14
    return-void

    .line 15
    :goto_0
    iget-object p0, p0, Landroidx/activity/ComponentActivity$$ExternalSyntheticLambda0;->f$0:Ljava/lang/Object;

    .line 16
    .line 17
    check-cast p0, Landroidx/activity/ComponentActivity$ReportFullyDrawnExecutorApi16Impl;

    .line 18
    .line 19
    iget-object v0, p0, Landroidx/activity/ComponentActivity$ReportFullyDrawnExecutorApi16Impl;->mRunnable:Ljava/lang/Runnable;

    .line 20
    .line 21
    if-eqz v0, :cond_0

    .line 22
    .line 23
    invoke-interface {v0}, Ljava/lang/Runnable;->run()V

    .line 24
    .line 25
    .line 26
    const/4 v0, 0x0

    .line 27
    iput-object v0, p0, Landroidx/activity/ComponentActivity$ReportFullyDrawnExecutorApi16Impl;->mRunnable:Ljava/lang/Runnable;

    .line 28
    .line 29
    :cond_0
    return-void

    .line 30
    nop

    .line 31
    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_0
    .end packed-switch
.end method