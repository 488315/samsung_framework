.class public final synthetic Lcom/android/systemui/qs/customize/SecQSCustomizerController$$ExternalSyntheticLambda2;
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
    iput p2, p0, Lcom/android/systemui/qs/customize/SecQSCustomizerController$$ExternalSyntheticLambda2;->$r8$classId:I

    .line 2
    .line 3
    iput-object p1, p0, Lcom/android/systemui/qs/customize/SecQSCustomizerController$$ExternalSyntheticLambda2;->f$0:Ljava/lang/Object;

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
    .locals 2

    .line 1
    iget v0, p0, Lcom/android/systemui/qs/customize/SecQSCustomizerController$$ExternalSyntheticLambda2;->$r8$classId:I

    .line 2
    .line 3
    const/4 v1, 0x1

    .line 4
    packed-switch v0, :pswitch_data_0

    .line 5
    .line 6
    .line 7
    goto :goto_0

    .line 8
    :pswitch_0
    iget-object p0, p0, Lcom/android/systemui/qs/customize/SecQSCustomizerController$$ExternalSyntheticLambda2;->f$0:Ljava/lang/Object;

    .line 9
    .line 10
    check-cast p0, Lcom/android/systemui/qs/customize/SecQSCustomizerController;

    .line 11
    .line 12
    iput-boolean v1, p0, Lcom/android/systemui/qs/customize/SecQSCustomizerController;->mIsReadyToMove:Z

    .line 13
    .line 14
    return-void

    .line 15
    :pswitch_1
    iget-object p0, p0, Lcom/android/systemui/qs/customize/SecQSCustomizerController$$ExternalSyntheticLambda2;->f$0:Ljava/lang/Object;

    .line 16
    .line 17
    check-cast p0, Lcom/android/systemui/qs/customize/SecQSCustomizerController;

    .line 18
    .line 19
    iput-boolean v1, p0, Lcom/android/systemui/qs/customize/SecQSCustomizerController;->mIsReadyToClick:Z

    .line 20
    .line 21
    return-void

    .line 22
    :pswitch_2
    iget-object p0, p0, Lcom/android/systemui/qs/customize/SecQSCustomizerController$$ExternalSyntheticLambda2;->f$0:Ljava/lang/Object;

    .line 23
    .line 24
    check-cast p0, Lcom/android/systemui/qs/customize/SecQSCustomizerController;

    .line 25
    .line 26
    iget-object p0, p0, Lcom/android/systemui/qs/customize/SecQSCustomizerController;->mDoneCallBack:Lcom/android/systemui/qs/customize/setting/SecQSSettingEditTilesActivity$onCreate$5;

    .line 27
    .line 28
    iget-object p0, p0, Lcom/android/systemui/qs/customize/setting/SecQSSettingEditTilesActivity$onCreate$5;->this$0:Lcom/android/systemui/qs/customize/setting/SecQSSettingEditTilesActivity;

    .line 29
    .line 30
    invoke-virtual {p0}, Landroid/app/Activity;->finish()V

    .line 31
    .line 32
    .line 33
    return-void

    .line 34
    :goto_0
    iget-object p0, p0, Lcom/android/systemui/qs/customize/SecQSCustomizerController$$ExternalSyntheticLambda2;->f$0:Ljava/lang/Object;

    .line 35
    .line 36
    check-cast p0, Lcom/android/systemui/qs/customize/SecQSCustomizerController$7;

    .line 37
    .line 38
    iget-object p0, p0, Lcom/android/systemui/qs/customize/SecQSCustomizerController$7;->this$0:Lcom/android/systemui/qs/customize/SecQSCustomizerController;

    .line 39
    .line 40
    iget-object v0, p0, Lcom/android/systemui/util/ViewController;->mView:Landroid/view/View;

    .line 41
    .line 42
    check-cast v0, Lcom/android/systemui/qs/customize/SecQSCustomizerBase;

    .line 43
    .line 44
    iget-boolean v0, v0, Lcom/android/systemui/qs/customize/SecQSCustomizerBase;->mIsDragging:Z

    .line 45
    .line 46
    if-nez v0, :cond_0

    .line 47
    .line 48
    iget-object v0, p0, Lcom/android/systemui/qs/customize/SecQSCustomizerController;->mLongClickedViewInfo:Lcom/android/systemui/qs/customize/SecQSCustomizerBase$CustomTileInfo;

    .line 49
    .line 50
    invoke-virtual {p0, v0}, Lcom/android/systemui/qs/customize/SecQSCustomizerController;->animationDrop(Lcom/android/systemui/qs/customize/SecQSCustomizerBase$CustomTileInfo;)V

    .line 51
    .line 52
    .line 53
    :cond_0
    return-void

    .line 54
    nop

    .line 55
    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method