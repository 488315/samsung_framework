.class public final Lcom/android/systemui/statusbar/KeyguardSecIndicationController$6;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/android/systemui/keyguard/WakefulnessLifecycle$Observer;


# instance fields
.field public final synthetic this$0:Lcom/android/systemui/statusbar/KeyguardSecIndicationController;


# direct methods
.method public constructor <init>(Lcom/android/systemui/statusbar/KeyguardSecIndicationController;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/statusbar/KeyguardSecIndicationController$6;->this$0:Lcom/android/systemui/statusbar/KeyguardSecIndicationController;

    .line 2
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final onFinishedGoingToSleep()V
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/android/systemui/statusbar/KeyguardSecIndicationController$6;->this$0:Lcom/android/systemui/statusbar/KeyguardSecIndicationController;

    .line 2
    .line 3
    iget-object p0, p0, Lcom/android/systemui/statusbar/KeyguardSecIndicationController;->mIndicationPolicy:Lcom/android/systemui/statusbar/KeyguardSecIndicationPolicy;

    .line 4
    .line 5
    if-eqz p0, :cond_0

    .line 6
    .line 7
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/KeyguardSecIndicationPolicy;->removeAllIndications()V

    .line 8
    .line 9
    .line 10
    :cond_0
    return-void
.end method

.method public final onStartedGoingToSleep()V
    .locals 1

    .line 1
    iget-object p0, p0, Lcom/android/systemui/statusbar/KeyguardSecIndicationController$6;->this$0:Lcom/android/systemui/statusbar/KeyguardSecIndicationController;

    .line 2
    .line 3
    const/4 v0, 0x0

    .line 4
    iput-boolean v0, p0, Lcom/android/systemui/statusbar/KeyguardSecIndicationController;->mIsScreenOn:Z

    .line 5
    .line 6
    return-void
.end method

.method public final onStartedWakingUp()V
    .locals 2

    .line 1
    const/4 v0, 0x1

    .line 2
    iget-object p0, p0, Lcom/android/systemui/statusbar/KeyguardSecIndicationController$6;->this$0:Lcom/android/systemui/statusbar/KeyguardSecIndicationController;

    .line 3
    .line 4
    iput-boolean v0, p0, Lcom/android/systemui/statusbar/KeyguardSecIndicationController;->mIsScreenOn:Z

    .line 5
    .line 6
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/KeyguardSecIndicationController;->addInitialIndication()V

    .line 7
    .line 8
    .line 9
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/KeyguardSecIndicationController;->addLifeStyleIndication()V

    .line 10
    .line 11
    .line 12
    sget-object v0, Lcom/android/systemui/statusbar/IndicationEventType;->UNLOCK_GUIDE:Lcom/android/systemui/statusbar/IndicationEventType;

    .line 13
    .line 14
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/KeyguardSecIndicationController;->getUnlockGuideText()Ljava/lang/CharSequence;

    .line 15
    .line 16
    .line 17
    move-result-object v1

    .line 18
    invoke-virtual {p0, v0, v1}, Lcom/android/systemui/statusbar/KeyguardSecIndicationController;->addIndication(Lcom/android/systemui/statusbar/IndicationEventType;Ljava/lang/CharSequence;)V

    .line 19
    .line 20
    .line 21
    return-void
.end method