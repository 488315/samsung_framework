.class public final synthetic Lcom/android/systemui/statusbar/policy/FlashlightControllerImpl$$ExternalSyntheticLambda0;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic $r8$classId:I

.field public final synthetic f$0:Lcom/android/systemui/statusbar/policy/FlashlightControllerImpl;


# direct methods
.method public synthetic constructor <init>(Lcom/android/systemui/statusbar/policy/FlashlightControllerImpl;I)V
    .locals 0

    .line 1
    iput p2, p0, Lcom/android/systemui/statusbar/policy/FlashlightControllerImpl$$ExternalSyntheticLambda0;->$r8$classId:I

    .line 2
    .line 3
    iput-object p1, p0, Lcom/android/systemui/statusbar/policy/FlashlightControllerImpl$$ExternalSyntheticLambda0;->f$0:Lcom/android/systemui/statusbar/policy/FlashlightControllerImpl;

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
    iget v0, p0, Lcom/android/systemui/statusbar/policy/FlashlightControllerImpl$$ExternalSyntheticLambda0;->$r8$classId:I

    .line 2
    .line 3
    packed-switch v0, :pswitch_data_0

    .line 4
    .line 5
    .line 6
    goto :goto_0

    .line 7
    :pswitch_0
    iget-object p0, p0, Lcom/android/systemui/statusbar/policy/FlashlightControllerImpl$$ExternalSyntheticLambda0;->f$0:Lcom/android/systemui/statusbar/policy/FlashlightControllerImpl;

    .line 8
    .line 9
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/policy/FlashlightControllerImpl;->tryInitCamera()V

    .line 10
    .line 11
    .line 12
    return-void

    .line 13
    :pswitch_1
    iget-object p0, p0, Lcom/android/systemui/statusbar/policy/FlashlightControllerImpl$$ExternalSyntheticLambda0;->f$0:Lcom/android/systemui/statusbar/policy/FlashlightControllerImpl;

    .line 14
    .line 15
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/policy/FlashlightControllerImpl;->tryInitCamera()V

    .line 16
    .line 17
    .line 18
    return-void

    .line 19
    :pswitch_2
    iget-object p0, p0, Lcom/android/systemui/statusbar/policy/FlashlightControllerImpl$$ExternalSyntheticLambda0;->f$0:Lcom/android/systemui/statusbar/policy/FlashlightControllerImpl;

    .line 20
    .line 21
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/policy/FlashlightControllerImpl;->tryInitCamera()V

    .line 22
    .line 23
    .line 24
    return-void

    .line 25
    :pswitch_3
    iget-object p0, p0, Lcom/android/systemui/statusbar/policy/FlashlightControllerImpl$$ExternalSyntheticLambda0;->f$0:Lcom/android/systemui/statusbar/policy/FlashlightControllerImpl;

    .line 26
    .line 27
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/policy/FlashlightControllerImpl;->tryInitCamera()V

    .line 28
    .line 29
    .line 30
    return-void

    .line 31
    :goto_0
    iget-object p0, p0, Lcom/android/systemui/statusbar/policy/FlashlightControllerImpl$$ExternalSyntheticLambda0;->f$0:Lcom/android/systemui/statusbar/policy/FlashlightControllerImpl;

    .line 32
    .line 33
    iget-object p0, p0, Lcom/android/systemui/statusbar/policy/FlashlightControllerImpl;->mSubscreenFlashlightController:Lcom/android/systemui/qp/flashlight/SubscreenFlashLightController;

    .line 34
    .line 35
    invoke-virtual {p0}, Lcom/android/systemui/qp/flashlight/SubscreenFlashLightController;->finishFlashLightActivity()V

    .line 36
    .line 37
    .line 38
    return-void

    .line 39
    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method