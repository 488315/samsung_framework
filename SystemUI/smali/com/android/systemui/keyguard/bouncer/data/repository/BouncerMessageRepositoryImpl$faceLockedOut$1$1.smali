.class final Lcom/android/systemui/keyguard/bouncer/data/repository/BouncerMessageRepositoryImpl$faceLockedOut$1$1;
.super Lkotlin/jvm/internal/Lambda;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lkotlin/jvm/functions/Function0;


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lkotlin/jvm/internal/Lambda;",
        "Lkotlin/jvm/functions/Function0;"
    }
.end annotation


# instance fields
.field final synthetic $callback:Lcom/android/systemui/keyguard/bouncer/data/repository/BouncerMessageRepositoryImpl$faceLockedOut$1$callback$1;

.field final synthetic $updateMonitor:Lcom/android/keyguard/KeyguardUpdateMonitor;


# direct methods
.method public constructor <init>(Lcom/android/keyguard/KeyguardUpdateMonitor;Lcom/android/systemui/keyguard/bouncer/data/repository/BouncerMessageRepositoryImpl$faceLockedOut$1$callback$1;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/keyguard/bouncer/data/repository/BouncerMessageRepositoryImpl$faceLockedOut$1$1;->$updateMonitor:Lcom/android/keyguard/KeyguardUpdateMonitor;

    .line 2
    .line 3
    iput-object p2, p0, Lcom/android/systemui/keyguard/bouncer/data/repository/BouncerMessageRepositoryImpl$faceLockedOut$1$1;->$callback:Lcom/android/systemui/keyguard/bouncer/data/repository/BouncerMessageRepositoryImpl$faceLockedOut$1$callback$1;

    .line 4
    .line 5
    const/4 p1, 0x0

    .line 6
    invoke-direct {p0, p1}, Lkotlin/jvm/internal/Lambda;-><init>(I)V

    .line 7
    .line 8
    .line 9
    return-void
.end method


# virtual methods
.method public final invoke()Ljava/lang/Object;
    .locals 1

    .line 1
    iget-object v0, p0, Lcom/android/systemui/keyguard/bouncer/data/repository/BouncerMessageRepositoryImpl$faceLockedOut$1$1;->$updateMonitor:Lcom/android/keyguard/KeyguardUpdateMonitor;

    .line 2
    .line 3
    iget-object p0, p0, Lcom/android/systemui/keyguard/bouncer/data/repository/BouncerMessageRepositoryImpl$faceLockedOut$1$1;->$callback:Lcom/android/systemui/keyguard/bouncer/data/repository/BouncerMessageRepositoryImpl$faceLockedOut$1$callback$1;

    .line 4
    .line 5
    invoke-virtual {v0, p0}, Lcom/android/keyguard/KeyguardUpdateMonitor;->removeCallback(Lcom/android/keyguard/KeyguardUpdateMonitorCallback;)V

    .line 6
    .line 7
    .line 8
    sget-object p0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;

    .line 9
    .line 10
    return-object p0
.end method
