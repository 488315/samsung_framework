.class public final enum Lcom/android/systemui/plank/command/PlankDispatcherFactory$DispatcherType;
.super Ljava/lang/Enum;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "Lcom/android/systemui/plank/command/PlankDispatcherFactory$DispatcherType;",
        ">;"
    }
.end annotation


# static fields
.field public static final synthetic $VALUES:[Lcom/android/systemui/plank/command/PlankDispatcherFactory$DispatcherType;

.field public static final enum global_action:Lcom/android/systemui/plank/command/PlankDispatcherFactory$DispatcherType;

.field public static final enum navigation_bar:Lcom/android/systemui/plank/command/PlankDispatcherFactory$DispatcherType;

.field public static final enum none:Lcom/android/systemui/plank/command/PlankDispatcherFactory$DispatcherType;


# direct methods
.method public static constructor <clinit>()V
    .locals 6

    .line 1
    new-instance v0, Lcom/android/systemui/plank/command/PlankDispatcherFactory$DispatcherType;

    .line 2
    .line 3
    const-string v1, "none"

    .line 4
    .line 5
    const/4 v2, 0x0

    .line 6
    invoke-direct {v0, v1, v2}, Lcom/android/systemui/plank/command/PlankDispatcherFactory$DispatcherType;-><init>(Ljava/lang/String;I)V

    .line 7
    .line 8
    .line 9
    sput-object v0, Lcom/android/systemui/plank/command/PlankDispatcherFactory$DispatcherType;->none:Lcom/android/systemui/plank/command/PlankDispatcherFactory$DispatcherType;

    .line 10
    .line 11
    new-instance v1, Lcom/android/systemui/plank/command/PlankDispatcherFactory$DispatcherType;

    .line 12
    .line 13
    const-string v2, "global_action"

    .line 14
    .line 15
    const/4 v3, 0x1

    .line 16
    invoke-direct {v1, v2, v3}, Lcom/android/systemui/plank/command/PlankDispatcherFactory$DispatcherType;-><init>(Ljava/lang/String;I)V

    .line 17
    .line 18
    .line 19
    sput-object v1, Lcom/android/systemui/plank/command/PlankDispatcherFactory$DispatcherType;->global_action:Lcom/android/systemui/plank/command/PlankDispatcherFactory$DispatcherType;

    .line 20
    .line 21
    new-instance v2, Lcom/android/systemui/plank/command/PlankDispatcherFactory$DispatcherType;

    .line 22
    .line 23
    const-string/jumbo v3, "volume_panel"

    .line 24
    .line 25
    .line 26
    const/4 v4, 0x2

    .line 27
    invoke-direct {v2, v3, v4}, Lcom/android/systemui/plank/command/PlankDispatcherFactory$DispatcherType;-><init>(Ljava/lang/String;I)V

    .line 28
    .line 29
    .line 30
    new-instance v3, Lcom/android/systemui/plank/command/PlankDispatcherFactory$DispatcherType;

    .line 31
    .line 32
    const-string v4, "navigation_bar"

    .line 33
    .line 34
    const/4 v5, 0x3

    .line 35
    invoke-direct {v3, v4, v5}, Lcom/android/systemui/plank/command/PlankDispatcherFactory$DispatcherType;-><init>(Ljava/lang/String;I)V

    .line 36
    .line 37
    .line 38
    sput-object v3, Lcom/android/systemui/plank/command/PlankDispatcherFactory$DispatcherType;->navigation_bar:Lcom/android/systemui/plank/command/PlankDispatcherFactory$DispatcherType;

    .line 39
    .line 40
    filled-new-array {v0, v1, v2, v3}, [Lcom/android/systemui/plank/command/PlankDispatcherFactory$DispatcherType;

    .line 41
    .line 42
    .line 43
    move-result-object v0

    .line 44
    sput-object v0, Lcom/android/systemui/plank/command/PlankDispatcherFactory$DispatcherType;->$VALUES:[Lcom/android/systemui/plank/command/PlankDispatcherFactory$DispatcherType;

    .line 45
    .line 46
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

.method public static valueOf(Ljava/lang/String;)Lcom/android/systemui/plank/command/PlankDispatcherFactory$DispatcherType;
    .locals 1

    .line 1
    const-class v0, Lcom/android/systemui/plank/command/PlankDispatcherFactory$DispatcherType;

    .line 2
    .line 3
    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    check-cast p0, Lcom/android/systemui/plank/command/PlankDispatcherFactory$DispatcherType;

    .line 8
    .line 9
    return-object p0
.end method

.method public static values()[Lcom/android/systemui/plank/command/PlankDispatcherFactory$DispatcherType;
    .locals 1

    .line 1
    sget-object v0, Lcom/android/systemui/plank/command/PlankDispatcherFactory$DispatcherType;->$VALUES:[Lcom/android/systemui/plank/command/PlankDispatcherFactory$DispatcherType;

    .line 2
    .line 3
    invoke-virtual {v0}, [Ljava/lang/Object;->clone()Ljava/lang/Object;

    .line 4
    .line 5
    .line 6
    move-result-object v0

    .line 7
    check-cast v0, [Lcom/android/systemui/plank/command/PlankDispatcherFactory$DispatcherType;

    .line 8
    .line 9
    return-object v0
.end method