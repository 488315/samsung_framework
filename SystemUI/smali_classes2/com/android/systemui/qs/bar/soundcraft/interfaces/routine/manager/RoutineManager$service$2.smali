.class final Lcom/android/systemui/qs/bar/soundcraft/interfaces/routine/manager/RoutineManager$service$2;
.super Lkotlin/jvm/internal/Lambda;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lkotlin/jvm/functions/Function0;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/systemui/qs/bar/soundcraft/interfaces/routine/manager/RoutineManager;-><init>(Landroid/content/Context;)V
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


# static fields
.field public static final INSTANCE:Lcom/android/systemui/qs/bar/soundcraft/interfaces/routine/manager/RoutineManager$service$2;


# direct methods
.method public static constructor <clinit>()V
    .locals 1

    .line 1
    new-instance v0, Lcom/android/systemui/qs/bar/soundcraft/interfaces/routine/manager/RoutineManager$service$2;

    .line 2
    .line 3
    invoke-direct {v0}, Lcom/android/systemui/qs/bar/soundcraft/interfaces/routine/manager/RoutineManager$service$2;-><init>()V

    .line 4
    .line 5
    .line 6
    sput-object v0, Lcom/android/systemui/qs/bar/soundcraft/interfaces/routine/manager/RoutineManager$service$2;->INSTANCE:Lcom/android/systemui/qs/bar/soundcraft/interfaces/routine/manager/RoutineManager$service$2;

    .line 7
    .line 8
    return-void
.end method

.method public constructor <init>()V
    .locals 1

    .line 1
    const/4 v0, 0x0

    .line 2
    invoke-direct {p0, v0}, Lkotlin/jvm/internal/Lambda;-><init>(I)V

    .line 3
    .line 4
    .line 5
    return-void
.end method


# virtual methods
.method public final invoke()Ljava/lang/Object;
    .locals 1

    .line 1
    sget-object p0, Lcom/samsung/android/sdk/routines/automationservice/AutomationServiceProvider;->INSTANCE:Lcom/samsung/android/sdk/routines/automationservice/AutomationServiceProvider;

    .line 2
    .line 3
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 4
    .line 5
    .line 6
    new-instance p0, Lcom/samsung/android/sdk/routines/automationservice/internal/AutomationServiceImpl;

    .line 7
    .line 8
    new-instance v0, Lcom/samsung/android/sdk/routines/automationservice/internal/ContentHandlerImpl;

    .line 9
    .line 10
    invoke-direct {v0}, Lcom/samsung/android/sdk/routines/automationservice/internal/ContentHandlerImpl;-><init>()V

    .line 11
    .line 12
    .line 13
    invoke-direct {p0, v0}, Lcom/samsung/android/sdk/routines/automationservice/internal/AutomationServiceImpl;-><init>(Lcom/samsung/android/sdk/routines/automationservice/interfaces/ContentHandler;)V

    .line 14
    .line 15
    .line 16
    return-object p0
.end method
