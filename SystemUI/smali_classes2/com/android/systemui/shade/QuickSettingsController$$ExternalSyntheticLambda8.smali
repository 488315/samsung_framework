.class public final synthetic Lcom/android/systemui/shade/QuickSettingsController$$ExternalSyntheticLambda8;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/util/function/Supplier;


# instance fields
.field public final synthetic $r8$classId:I

.field public final synthetic f$0:Ljava/lang/Object;


# direct methods
.method public synthetic constructor <init>(Ljava/lang/Object;I)V
    .locals 0

    .line 1
    iput p2, p0, Lcom/android/systemui/shade/QuickSettingsController$$ExternalSyntheticLambda8;->$r8$classId:I

    .line 2
    .line 3
    iput-object p1, p0, Lcom/android/systemui/shade/QuickSettingsController$$ExternalSyntheticLambda8;->f$0:Ljava/lang/Object;

    .line 4
    .line 5
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 6
    .line 7
    .line 8
    return-void
.end method


# virtual methods
.method public final get()Ljava/lang/Object;
    .locals 1

    .line 1
    iget v0, p0, Lcom/android/systemui/shade/QuickSettingsController$$ExternalSyntheticLambda8;->$r8$classId:I

    .line 2
    .line 3
    packed-switch v0, :pswitch_data_0

    .line 4
    .line 5
    .line 6
    goto :goto_0

    .line 7
    :pswitch_0
    iget-object p0, p0, Lcom/android/systemui/shade/QuickSettingsController$$ExternalSyntheticLambda8;->f$0:Ljava/lang/Object;

    .line 8
    .line 9
    check-cast p0, Lcom/android/systemui/shade/QuickSettingsController;

    .line 10
    .line 11
    iget-object p0, p0, Lcom/android/systemui/shade/QuickSettingsController;->mQs:Lcom/android/systemui/plugins/qs/QS;

    .line 12
    .line 13
    return-object p0

    .line 14
    :pswitch_1
    iget-object p0, p0, Lcom/android/systemui/shade/QuickSettingsController$$ExternalSyntheticLambda8;->f$0:Ljava/lang/Object;

    .line 15
    .line 16
    check-cast p0, Lcom/android/systemui/shade/QuickSettingsController;

    .line 17
    .line 18
    iget-object p0, p0, Lcom/android/systemui/shade/QuickSettingsController;->mQsFrame:Landroid/widget/FrameLayout;

    .line 19
    .line 20
    return-object p0

    .line 21
    :goto_0
    iget-object p0, p0, Lcom/android/systemui/shade/QuickSettingsController$$ExternalSyntheticLambda8;->f$0:Ljava/lang/Object;

    .line 22
    .line 23
    check-cast p0, Ldagger/Lazy;

    .line 24
    .line 25
    invoke-interface {p0}, Ldagger/Lazy;->get()Ljava/lang/Object;

    .line 26
    .line 27
    .line 28
    move-result-object p0

    .line 29
    check-cast p0, Lcom/android/systemui/shade/NotificationPanelViewController;

    .line 30
    .line 31
    iget-object p0, p0, Lcom/android/systemui/shade/NotificationPanelViewController;->mView:Lcom/android/systemui/shade/NotificationPanelView;

    .line 32
    .line 33
    return-object p0

    .line 34
    nop

    .line 35
    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method
