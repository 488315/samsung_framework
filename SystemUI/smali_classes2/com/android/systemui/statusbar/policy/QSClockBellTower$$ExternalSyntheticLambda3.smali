.class public final synthetic Lcom/android/systemui/statusbar/policy/QSClockBellTower$$ExternalSyntheticLambda3;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Ljava/util/function/Consumer;


# instance fields
.field public final synthetic f$0:Lcom/android/systemui/statusbar/policy/QSClockBellSound;


# direct methods
.method public synthetic constructor <init>(Lcom/android/systemui/statusbar/policy/QSClockBellSound;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lcom/android/systemui/statusbar/policy/QSClockBellTower$$ExternalSyntheticLambda3;->f$0:Lcom/android/systemui/statusbar/policy/QSClockBellSound;

    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final accept(Ljava/lang/Object;)V
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/android/systemui/statusbar/policy/QSClockBellTower$$ExternalSyntheticLambda3;->f$0:Lcom/android/systemui/statusbar/policy/QSClockBellSound;

    .line 2
    .line 3
    check-cast p1, Lcom/android/systemui/statusbar/policy/QSClockBellTower$TimeAudience;

    .line 4
    .line 5
    invoke-interface {p1, p0}, Lcom/android/systemui/statusbar/policy/QSClockBellTower$TimeAudience;->notifyTimeChanged(Lcom/android/systemui/statusbar/policy/QSClockBellSound;)V

    .line 6
    .line 7
    .line 8
    return-void
.end method
