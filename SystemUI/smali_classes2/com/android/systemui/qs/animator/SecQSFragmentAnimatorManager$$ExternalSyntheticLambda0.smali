.class public final synthetic Lcom/android/systemui/qs/animator/SecQSFragmentAnimatorManager$$ExternalSyntheticLambda0;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/util/function/Consumer;


# instance fields
.field public final synthetic $r8$classId:I


# direct methods
.method public synthetic constructor <init>(I)V
    .locals 0

    .line 1
    iput p1, p0, Lcom/android/systemui/qs/animator/SecQSFragmentAnimatorManager$$ExternalSyntheticLambda0;->$r8$classId:I

    .line 2
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final accept(Ljava/lang/Object;)V
    .locals 0

    .line 1
    iget p0, p0, Lcom/android/systemui/qs/animator/SecQSFragmentAnimatorManager$$ExternalSyntheticLambda0;->$r8$classId:I

    .line 2
    .line 3
    packed-switch p0, :pswitch_data_0

    .line 4
    .line 5
    .line 6
    goto :goto_0

    .line 7
    :pswitch_0
    check-cast p1, Lcom/android/systemui/qs/animator/SecQSFragmentAnimatorBase;

    .line 8
    .line 9
    invoke-virtual {p1}, Lcom/android/systemui/qs/animator/SecQSFragmentAnimatorBase;->destroyQSViews()V

    .line 10
    .line 11
    .line 12
    return-void

    .line 13
    :pswitch_1
    check-cast p1, Lcom/android/systemui/qs/animator/SecQSFragmentAnimatorBase;

    .line 14
    .line 15
    invoke-virtual {p1}, Lcom/android/systemui/qs/animator/SecQSFragmentAnimatorBase;->onPanelClosed()V

    .line 16
    .line 17
    .line 18
    return-void

    .line 19
    :pswitch_2
    check-cast p1, Lcom/android/systemui/qs/animator/SecQSFragmentAnimatorBase;

    .line 20
    .line 21
    invoke-virtual {p1}, Lcom/android/systemui/qs/animator/SecQSFragmentAnimatorBase;->onRtlChanged()V

    .line 22
    .line 23
    .line 24
    return-void

    .line 25
    :goto_0
    check-cast p1, Lcom/android/systemui/qs/animator/SecQSFragmentAnimatorBase;

    .line 26
    .line 27
    invoke-virtual {p1}, Lcom/android/systemui/qs/animator/SecQSFragmentAnimatorBase;->updateAnimators()V

    .line 28
    .line 29
    .line 30
    return-void

    .line 31
    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method