.class final enum Lcom/android/systemui/statusbar/policy/LocationControllerImpl$LocationIndicatorEvent;
.super Ljava/lang/Enum;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/android/internal/logging/UiEventLogger$UiEventEnum;


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "Lcom/android/systemui/statusbar/policy/LocationControllerImpl$LocationIndicatorEvent;",
        ">;",
        "Lcom/android/internal/logging/UiEventLogger$UiEventEnum;"
    }
.end annotation


# static fields
.field public static final synthetic $VALUES:[Lcom/android/systemui/statusbar/policy/LocationControllerImpl$LocationIndicatorEvent;

.field public static final enum LOCATION_INDICATOR_MONITOR_HIGH_POWER:Lcom/android/systemui/statusbar/policy/LocationControllerImpl$LocationIndicatorEvent;

.field public static final enum LOCATION_INDICATOR_NON_SYSTEM_APP:Lcom/android/systemui/statusbar/policy/LocationControllerImpl$LocationIndicatorEvent;

.field public static final enum LOCATION_INDICATOR_SYSTEM_APP:Lcom/android/systemui/statusbar/policy/LocationControllerImpl$LocationIndicatorEvent;


# instance fields
.field private final mId:I


# direct methods
.method public static constructor <clinit>()V
    .locals 6

    .line 1
    new-instance v0, Lcom/android/systemui/statusbar/policy/LocationControllerImpl$LocationIndicatorEvent;

    .line 2
    .line 3
    const/4 v1, 0x0

    .line 4
    const/16 v2, 0x3a7

    .line 5
    .line 6
    const-string v3, "LOCATION_INDICATOR_MONITOR_HIGH_POWER"

    .line 7
    .line 8
    invoke-direct {v0, v3, v1, v2}, Lcom/android/systemui/statusbar/policy/LocationControllerImpl$LocationIndicatorEvent;-><init>(Ljava/lang/String;II)V

    .line 9
    .line 10
    .line 11
    sput-object v0, Lcom/android/systemui/statusbar/policy/LocationControllerImpl$LocationIndicatorEvent;->LOCATION_INDICATOR_MONITOR_HIGH_POWER:Lcom/android/systemui/statusbar/policy/LocationControllerImpl$LocationIndicatorEvent;

    .line 12
    .line 13
    new-instance v1, Lcom/android/systemui/statusbar/policy/LocationControllerImpl$LocationIndicatorEvent;

    .line 14
    .line 15
    const/4 v2, 0x1

    .line 16
    const/16 v3, 0x3a8

    .line 17
    .line 18
    const-string v4, "LOCATION_INDICATOR_SYSTEM_APP"

    .line 19
    .line 20
    invoke-direct {v1, v4, v2, v3}, Lcom/android/systemui/statusbar/policy/LocationControllerImpl$LocationIndicatorEvent;-><init>(Ljava/lang/String;II)V

    .line 21
    .line 22
    .line 23
    sput-object v1, Lcom/android/systemui/statusbar/policy/LocationControllerImpl$LocationIndicatorEvent;->LOCATION_INDICATOR_SYSTEM_APP:Lcom/android/systemui/statusbar/policy/LocationControllerImpl$LocationIndicatorEvent;

    .line 24
    .line 25
    new-instance v2, Lcom/android/systemui/statusbar/policy/LocationControllerImpl$LocationIndicatorEvent;

    .line 26
    .line 27
    const/4 v3, 0x2

    .line 28
    const/16 v4, 0x3a9

    .line 29
    .line 30
    const-string v5, "LOCATION_INDICATOR_NON_SYSTEM_APP"

    .line 31
    .line 32
    invoke-direct {v2, v5, v3, v4}, Lcom/android/systemui/statusbar/policy/LocationControllerImpl$LocationIndicatorEvent;-><init>(Ljava/lang/String;II)V

    .line 33
    .line 34
    .line 35
    sput-object v2, Lcom/android/systemui/statusbar/policy/LocationControllerImpl$LocationIndicatorEvent;->LOCATION_INDICATOR_NON_SYSTEM_APP:Lcom/android/systemui/statusbar/policy/LocationControllerImpl$LocationIndicatorEvent;

    .line 36
    .line 37
    filled-new-array {v0, v1, v2}, [Lcom/android/systemui/statusbar/policy/LocationControllerImpl$LocationIndicatorEvent;

    .line 38
    .line 39
    .line 40
    move-result-object v0

    .line 41
    sput-object v0, Lcom/android/systemui/statusbar/policy/LocationControllerImpl$LocationIndicatorEvent;->$VALUES:[Lcom/android/systemui/statusbar/policy/LocationControllerImpl$LocationIndicatorEvent;

    .line 42
    .line 43
    return-void
.end method

.method private constructor <init>(Ljava/lang/String;II)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(I)V"
        }
    .end annotation

    .line 1
    invoke-direct {p0, p1, p2}, Ljava/lang/Enum;-><init>(Ljava/lang/String;I)V

    .line 2
    .line 3
    .line 4
    iput p3, p0, Lcom/android/systemui/statusbar/policy/LocationControllerImpl$LocationIndicatorEvent;->mId:I

    .line 5
    .line 6
    return-void
.end method

.method public static valueOf(Ljava/lang/String;)Lcom/android/systemui/statusbar/policy/LocationControllerImpl$LocationIndicatorEvent;
    .locals 1

    .line 1
    const-class v0, Lcom/android/systemui/statusbar/policy/LocationControllerImpl$LocationIndicatorEvent;

    .line 2
    .line 3
    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    check-cast p0, Lcom/android/systemui/statusbar/policy/LocationControllerImpl$LocationIndicatorEvent;

    .line 8
    .line 9
    return-object p0
.end method

.method public static values()[Lcom/android/systemui/statusbar/policy/LocationControllerImpl$LocationIndicatorEvent;
    .locals 1

    .line 1
    sget-object v0, Lcom/android/systemui/statusbar/policy/LocationControllerImpl$LocationIndicatorEvent;->$VALUES:[Lcom/android/systemui/statusbar/policy/LocationControllerImpl$LocationIndicatorEvent;

    .line 2
    .line 3
    invoke-virtual {v0}, [Lcom/android/systemui/statusbar/policy/LocationControllerImpl$LocationIndicatorEvent;->clone()Ljava/lang/Object;

    .line 4
    .line 5
    .line 6
    move-result-object v0

    .line 7
    check-cast v0, [Lcom/android/systemui/statusbar/policy/LocationControllerImpl$LocationIndicatorEvent;

    .line 8
    .line 9
    return-object v0
.end method


# virtual methods
.method public final getId()I
    .locals 0

    .line 1
    iget p0, p0, Lcom/android/systemui/statusbar/policy/LocationControllerImpl$LocationIndicatorEvent;->mId:I

    .line 2
    .line 3
    return p0
.end method