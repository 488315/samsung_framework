.class final Lcom/android/keyguard/emm/EngineeringModeManagerWrapper$emm$2;
.super Lkotlin/jvm/internal/Lambda;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lkotlin/jvm/functions/Function0;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/keyguard/emm/EngineeringModeManagerWrapper;-><init>(Landroid/content/Context;Lkotlinx/coroutines/CoroutineScope;Lcom/android/systemui/statusbar/policy/KeyguardStateController;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x19
    name = null
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lkotlin/jvm/internal/Lambda;",
        "Lkotlin/jvm/functions/Function0;"
    }
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/keyguard/emm/EngineeringModeManagerWrapper;


# direct methods
.method public constructor <init>(Lcom/android/keyguard/emm/EngineeringModeManagerWrapper;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/keyguard/emm/EngineeringModeManagerWrapper$emm$2;->this$0:Lcom/android/keyguard/emm/EngineeringModeManagerWrapper;

    .line 2
    .line 3
    const/4 p1, 0x0

    .line 4
    invoke-direct {p0, p1}, Lkotlin/jvm/internal/Lambda;-><init>(I)V

    .line 5
    .line 6
    .line 7
    return-void
.end method


# virtual methods
.method public final invoke()Ljava/lang/Object;
    .locals 1

    .line 1
    new-instance v0, Lcom/samsung/android/service/EngineeringMode/EngineeringModeManager;

    .line 2
    .line 3
    iget-object p0, p0, Lcom/android/keyguard/emm/EngineeringModeManagerWrapper$emm$2;->this$0:Lcom/android/keyguard/emm/EngineeringModeManagerWrapper;

    .line 4
    .line 5
    iget-object p0, p0, Lcom/android/keyguard/emm/EngineeringModeManagerWrapper;->context:Landroid/content/Context;

    .line 6
    .line 7
    invoke-direct {v0, p0}, Lcom/samsung/android/service/EngineeringMode/EngineeringModeManager;-><init>(Landroid/content/Context;)V

    .line 8
    .line 9
    .line 10
    return-object v0
.end method
