.class public final enum Lcom/android/systemui/keyguard/shared/model/DevicePosture;
.super Ljava/lang/Enum;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "Lcom/android/systemui/keyguard/shared/model/DevicePosture;",
        ">;"
    }
.end annotation


# static fields
.field public static final synthetic $VALUES:[Lcom/android/systemui/keyguard/shared/model/DevicePosture;

.field public static final enum CLOSED:Lcom/android/systemui/keyguard/shared/model/DevicePosture;

.field public static final Companion:Lcom/android/systemui/keyguard/shared/model/DevicePosture$Companion;

.field public static final enum FLIPPED:Lcom/android/systemui/keyguard/shared/model/DevicePosture;

.field public static final enum HALF_OPENED:Lcom/android/systemui/keyguard/shared/model/DevicePosture;

.field public static final enum OPENED:Lcom/android/systemui/keyguard/shared/model/DevicePosture;

.field public static final enum UNKNOWN:Lcom/android/systemui/keyguard/shared/model/DevicePosture;


# direct methods
.method public static constructor <clinit>()V
    .locals 7

    .line 1
    new-instance v0, Lcom/android/systemui/keyguard/shared/model/DevicePosture;

    .line 2
    .line 3
    const-string v1, "UNKNOWN"

    .line 4
    .line 5
    const/4 v2, 0x0

    .line 6
    invoke-direct {v0, v1, v2}, Lcom/android/systemui/keyguard/shared/model/DevicePosture;-><init>(Ljava/lang/String;I)V

    .line 7
    .line 8
    .line 9
    sput-object v0, Lcom/android/systemui/keyguard/shared/model/DevicePosture;->UNKNOWN:Lcom/android/systemui/keyguard/shared/model/DevicePosture;

    .line 10
    .line 11
    new-instance v1, Lcom/android/systemui/keyguard/shared/model/DevicePosture;

    .line 12
    .line 13
    const-string v2, "CLOSED"

    .line 14
    .line 15
    const/4 v3, 0x1

    .line 16
    invoke-direct {v1, v2, v3}, Lcom/android/systemui/keyguard/shared/model/DevicePosture;-><init>(Ljava/lang/String;I)V

    .line 17
    .line 18
    .line 19
    sput-object v1, Lcom/android/systemui/keyguard/shared/model/DevicePosture;->CLOSED:Lcom/android/systemui/keyguard/shared/model/DevicePosture;

    .line 20
    .line 21
    new-instance v2, Lcom/android/systemui/keyguard/shared/model/DevicePosture;

    .line 22
    .line 23
    const-string v3, "HALF_OPENED"

    .line 24
    .line 25
    const/4 v4, 0x2

    .line 26
    invoke-direct {v2, v3, v4}, Lcom/android/systemui/keyguard/shared/model/DevicePosture;-><init>(Ljava/lang/String;I)V

    .line 27
    .line 28
    .line 29
    sput-object v2, Lcom/android/systemui/keyguard/shared/model/DevicePosture;->HALF_OPENED:Lcom/android/systemui/keyguard/shared/model/DevicePosture;

    .line 30
    .line 31
    new-instance v3, Lcom/android/systemui/keyguard/shared/model/DevicePosture;

    .line 32
    .line 33
    const-string v4, "OPENED"

    .line 34
    .line 35
    const/4 v5, 0x3

    .line 36
    invoke-direct {v3, v4, v5}, Lcom/android/systemui/keyguard/shared/model/DevicePosture;-><init>(Ljava/lang/String;I)V

    .line 37
    .line 38
    .line 39
    sput-object v3, Lcom/android/systemui/keyguard/shared/model/DevicePosture;->OPENED:Lcom/android/systemui/keyguard/shared/model/DevicePosture;

    .line 40
    .line 41
    new-instance v4, Lcom/android/systemui/keyguard/shared/model/DevicePosture;

    .line 42
    .line 43
    const-string v5, "FLIPPED"

    .line 44
    .line 45
    const/4 v6, 0x4

    .line 46
    invoke-direct {v4, v5, v6}, Lcom/android/systemui/keyguard/shared/model/DevicePosture;-><init>(Ljava/lang/String;I)V

    .line 47
    .line 48
    .line 49
    sput-object v4, Lcom/android/systemui/keyguard/shared/model/DevicePosture;->FLIPPED:Lcom/android/systemui/keyguard/shared/model/DevicePosture;

    .line 50
    .line 51
    filled-new-array {v0, v1, v2, v3, v4}, [Lcom/android/systemui/keyguard/shared/model/DevicePosture;

    .line 52
    .line 53
    .line 54
    move-result-object v0

    .line 55
    sput-object v0, Lcom/android/systemui/keyguard/shared/model/DevicePosture;->$VALUES:[Lcom/android/systemui/keyguard/shared/model/DevicePosture;

    .line 56
    .line 57
    new-instance v0, Lcom/android/systemui/keyguard/shared/model/DevicePosture$Companion;

    .line 58
    .line 59
    const/4 v1, 0x0

    .line 60
    invoke-direct {v0, v1}, Lcom/android/systemui/keyguard/shared/model/DevicePosture$Companion;-><init>(Lkotlin/jvm/internal/DefaultConstructorMarker;)V

    .line 61
    .line 62
    .line 63
    sput-object v0, Lcom/android/systemui/keyguard/shared/model/DevicePosture;->Companion:Lcom/android/systemui/keyguard/shared/model/DevicePosture$Companion;

    .line 64
    .line 65
    return-void
.end method

.method private constructor <init>(Ljava/lang/String;I)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 1
    invoke-direct {p0, p1, p2}, Ljava/lang/Enum;-><init>(Ljava/lang/String;I)V

    .line 2
    .line 3
    .line 4
    return-void
.end method

.method public static valueOf(Ljava/lang/String;)Lcom/android/systemui/keyguard/shared/model/DevicePosture;
    .locals 1

    .line 1
    const-class v0, Lcom/android/systemui/keyguard/shared/model/DevicePosture;

    .line 2
    .line 3
    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    check-cast p0, Lcom/android/systemui/keyguard/shared/model/DevicePosture;

    .line 8
    .line 9
    return-object p0
.end method

.method public static values()[Lcom/android/systemui/keyguard/shared/model/DevicePosture;
    .locals 1

    .line 1
    sget-object v0, Lcom/android/systemui/keyguard/shared/model/DevicePosture;->$VALUES:[Lcom/android/systemui/keyguard/shared/model/DevicePosture;

    .line 2
    .line 3
    invoke-virtual {v0}, [Ljava/lang/Object;->clone()Ljava/lang/Object;

    .line 4
    .line 5
    .line 6
    move-result-object v0

    .line 7
    check-cast v0, [Lcom/android/systemui/keyguard/shared/model/DevicePosture;

    .line 8
    .line 9
    return-object v0
.end method
