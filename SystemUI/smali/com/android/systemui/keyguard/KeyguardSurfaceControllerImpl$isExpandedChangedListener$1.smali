.class final synthetic Lcom/android/systemui/keyguard/KeyguardSurfaceControllerImpl$isExpandedChangedListener$1;
.super Lkotlin/jvm/internal/FunctionReferenceImpl;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lkotlin/jvm/functions/Function1;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/systemui/keyguard/KeyguardSurfaceControllerImpl;-><init>(Lcom/android/keyguard/KeyguardViewController;Lcom/android/systemui/keyguard/KeyguardVisibilityMonitor;Ldagger/Lazy;Ljava/util/concurrent/Executor;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x1001
    name = null
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lkotlin/jvm/internal/FunctionReferenceImpl;",
        "Lkotlin/jvm/functions/Function1;"
    }
.end annotation


# direct methods
.method public constructor <init>(Ljava/lang/Object;)V
    .locals 7

    .line 1
    const/4 v1, 0x1

    .line 2
    const-class v3, Lcom/android/systemui/keyguard/KeyguardSurfaceControllerImpl;

    .line 3
    .line 4
    const-string v4, "internalRestoreKeyguardSurfaceIfVisible"

    .line 5
    .line 6
    const-string v5, "internalRestoreKeyguardSurfaceIfVisible(Z)V"

    .line 7
    .line 8
    const/4 v6, 0x0

    .line 9
    move-object v0, p0

    .line 10
    move-object v2, p1

    .line 11
    invoke-direct/range {v0 .. v6}, Lkotlin/jvm/internal/FunctionReferenceImpl;-><init>(ILjava/lang/Object;Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;I)V

    .line 12
    .line 13
    .line 14
    return-void
.end method


# virtual methods
.method public final invoke(Ljava/lang/Object;)Ljava/lang/Object;
    .locals 0

    .line 1
    check-cast p1, Ljava/lang/Boolean;

    .line 2
    .line 3
    invoke-virtual {p1}, Ljava/lang/Boolean;->booleanValue()Z

    .line 4
    .line 5
    .line 6
    move-result p1

    .line 7
    iget-object p0, p0, Lkotlin/jvm/internal/CallableReference;->receiver:Ljava/lang/Object;

    .line 8
    .line 9
    check-cast p0, Lcom/android/systemui/keyguard/KeyguardSurfaceControllerImpl;

    .line 10
    .line 11
    invoke-virtual {p0, p1}, Lcom/android/systemui/keyguard/KeyguardSurfaceControllerImpl;->internalRestoreKeyguardSurfaceIfVisible(Z)V

    .line 12
    .line 13
    .line 14
    sget-object p0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;

    .line 15
    .line 16
    return-object p0
.end method