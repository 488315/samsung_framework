.class final Lcom/android/systemui/statusbar/pipeline/mobile/data/repository/DummyMobileConnectionRepository$serviceStateBroadcast$1;
.super Lkotlin/jvm/internal/Lambda;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lkotlin/jvm/functions/Function2;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/systemui/statusbar/pipeline/mobile/data/repository/DummyMobileConnectionRepository;-><init>(ILkotlinx/coroutines/CoroutineScope;Lcom/android/systemui/broadcast/BroadcastDispatcher;Lcom/android/systemui/statusbar/pipeline/mobile/data/model/NetworkNameModel;Ljava/lang/String;Lcom/android/systemui/statusbar/pipeline/mobile/data/MobileInputLogger;Lcom/android/systemui/log/table/TableLogBuffer;Lcom/android/systemui/statusbar/pipeline/carrier/CarrierInfraMediator;Landroid/telephony/TelephonyManager;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x19
    name = null
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lkotlin/jvm/internal/Lambda;",
        "Lkotlin/jvm/functions/Function2;"
    }
.end annotation


# static fields
.field public static final INSTANCE:Lcom/android/systemui/statusbar/pipeline/mobile/data/repository/DummyMobileConnectionRepository$serviceStateBroadcast$1;


# direct methods
.method public static constructor <clinit>()V
    .locals 1

    .line 1
    new-instance v0, Lcom/android/systemui/statusbar/pipeline/mobile/data/repository/DummyMobileConnectionRepository$serviceStateBroadcast$1;

    .line 2
    .line 3
    invoke-direct {v0}, Lcom/android/systemui/statusbar/pipeline/mobile/data/repository/DummyMobileConnectionRepository$serviceStateBroadcast$1;-><init>()V

    .line 4
    .line 5
    .line 6
    sput-object v0, Lcom/android/systemui/statusbar/pipeline/mobile/data/repository/DummyMobileConnectionRepository$serviceStateBroadcast$1;->INSTANCE:Lcom/android/systemui/statusbar/pipeline/mobile/data/repository/DummyMobileConnectionRepository$serviceStateBroadcast$1;

    .line 7
    .line 8
    return-void
.end method

.method public constructor <init>()V
    .locals 1

    .line 1
    const/4 v0, 0x2

    .line 2
    invoke-direct {p0, v0}, Lkotlin/jvm/internal/Lambda;-><init>(I)V

    .line 3
    .line 4
    .line 5
    return-void
.end method


# virtual methods
.method public final invoke(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    .locals 0

    .line 1
    check-cast p1, Landroid/content/Intent;

    .line 2
    .line 3
    check-cast p2, Landroid/content/BroadcastReceiver;

    .line 4
    .line 5
    return-object p1
.end method