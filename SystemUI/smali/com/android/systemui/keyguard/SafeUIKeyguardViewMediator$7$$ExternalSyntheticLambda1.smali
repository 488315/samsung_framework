.class public final synthetic Lcom/android/systemui/keyguard/SafeUIKeyguardViewMediator$7$$ExternalSyntheticLambda1;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic $r8$classId:I

.field public final synthetic f$0:Landroid/view/IRemoteAnimationRunner$Stub;


# direct methods
.method public synthetic constructor <init>(Landroid/view/IRemoteAnimationRunner$Stub;I)V
    .locals 0

    .line 1
    iput p2, p0, Lcom/android/systemui/keyguard/SafeUIKeyguardViewMediator$7$$ExternalSyntheticLambda1;->$r8$classId:I

    .line 2
    .line 3
    iput-object p1, p0, Lcom/android/systemui/keyguard/SafeUIKeyguardViewMediator$7$$ExternalSyntheticLambda1;->f$0:Landroid/view/IRemoteAnimationRunner$Stub;

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
    iget v0, p0, Lcom/android/systemui/keyguard/SafeUIKeyguardViewMediator$7$$ExternalSyntheticLambda1;->$r8$classId:I

    .line 2
    .line 3
    packed-switch v0, :pswitch_data_0

    .line 4
    .line 5
    .line 6
    goto :goto_0

    .line 7
    :pswitch_0
    iget-object p0, p0, Lcom/android/systemui/keyguard/SafeUIKeyguardViewMediator$7$$ExternalSyntheticLambda1;->f$0:Landroid/view/IRemoteAnimationRunner$Stub;

    .line 8
    .line 9
    check-cast p0, Lcom/android/systemui/keyguard/SafeUIKeyguardViewMediator$7;

    .line 10
    .line 11
    iget-object p0, p0, Lcom/android/systemui/keyguard/SafeUIKeyguardViewMediator$7;->mOccludeByDreamAnimator:Landroid/animation/ValueAnimator;

    .line 12
    .line 13
    if-eqz p0, :cond_0

    .line 14
    .line 15
    invoke-virtual {p0}, Landroid/animation/ValueAnimator;->cancel()V

    .line 16
    .line 17
    .line 18
    :cond_0
    return-void

    .line 19
    :goto_0
    iget-object p0, p0, Lcom/android/systemui/keyguard/SafeUIKeyguardViewMediator$7$$ExternalSyntheticLambda1;->f$0:Landroid/view/IRemoteAnimationRunner$Stub;

    .line 20
    .line 21
    check-cast p0, Lcom/android/systemui/keyguard/SafeUIKeyguardViewMediator$8;

    .line 22
    .line 23
    sget v0, Lcom/android/systemui/keyguard/SafeUIKeyguardViewMediator$8;->$r8$clinit:I

    .line 24
    .line 25
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 26
    .line 27
    .line 28
    return-void

    .line 29
    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_0
    .end packed-switch
.end method