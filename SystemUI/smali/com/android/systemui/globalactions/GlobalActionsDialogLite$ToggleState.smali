.class final enum Lcom/android/systemui/globalactions/GlobalActionsDialogLite$ToggleState;
.super Ljava/lang/Enum;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "Lcom/android/systemui/globalactions/GlobalActionsDialogLite$ToggleState;",
        ">;"
    }
.end annotation


# static fields
.field public static final synthetic $VALUES:[Lcom/android/systemui/globalactions/GlobalActionsDialogLite$ToggleState;

.field public static final enum Off:Lcom/android/systemui/globalactions/GlobalActionsDialogLite$ToggleState;

.field public static final enum On:Lcom/android/systemui/globalactions/GlobalActionsDialogLite$ToggleState;

.field public static final enum TurningOff:Lcom/android/systemui/globalactions/GlobalActionsDialogLite$ToggleState;

.field public static final enum TurningOn:Lcom/android/systemui/globalactions/GlobalActionsDialogLite$ToggleState;


# instance fields
.field private final mInTransition:Z


# direct methods
.method public static constructor <clinit>()V
    .locals 7

    .line 1
    new-instance v0, Lcom/android/systemui/globalactions/GlobalActionsDialogLite$ToggleState;

    .line 2
    .line 3
    const-string v1, "Off"

    .line 4
    .line 5
    const/4 v2, 0x0

    .line 6
    invoke-direct {v0, v1, v2, v2}, Lcom/android/systemui/globalactions/GlobalActionsDialogLite$ToggleState;-><init>(Ljava/lang/String;IZ)V

    .line 7
    .line 8
    .line 9
    sput-object v0, Lcom/android/systemui/globalactions/GlobalActionsDialogLite$ToggleState;->Off:Lcom/android/systemui/globalactions/GlobalActionsDialogLite$ToggleState;

    .line 10
    .line 11
    new-instance v1, Lcom/android/systemui/globalactions/GlobalActionsDialogLite$ToggleState;

    .line 12
    .line 13
    const-string v3, "TurningOn"

    .line 14
    .line 15
    const/4 v4, 0x1

    .line 16
    invoke-direct {v1, v3, v4, v4}, Lcom/android/systemui/globalactions/GlobalActionsDialogLite$ToggleState;-><init>(Ljava/lang/String;IZ)V

    .line 17
    .line 18
    .line 19
    sput-object v1, Lcom/android/systemui/globalactions/GlobalActionsDialogLite$ToggleState;->TurningOn:Lcom/android/systemui/globalactions/GlobalActionsDialogLite$ToggleState;

    .line 20
    .line 21
    new-instance v3, Lcom/android/systemui/globalactions/GlobalActionsDialogLite$ToggleState;

    .line 22
    .line 23
    const-string v5, "TurningOff"

    .line 24
    .line 25
    const/4 v6, 0x2

    .line 26
    invoke-direct {v3, v5, v6, v4}, Lcom/android/systemui/globalactions/GlobalActionsDialogLite$ToggleState;-><init>(Ljava/lang/String;IZ)V

    .line 27
    .line 28
    .line 29
    sput-object v3, Lcom/android/systemui/globalactions/GlobalActionsDialogLite$ToggleState;->TurningOff:Lcom/android/systemui/globalactions/GlobalActionsDialogLite$ToggleState;

    .line 30
    .line 31
    new-instance v4, Lcom/android/systemui/globalactions/GlobalActionsDialogLite$ToggleState;

    .line 32
    .line 33
    const-string v5, "On"

    .line 34
    .line 35
    const/4 v6, 0x3

    .line 36
    invoke-direct {v4, v5, v6, v2}, Lcom/android/systemui/globalactions/GlobalActionsDialogLite$ToggleState;-><init>(Ljava/lang/String;IZ)V

    .line 37
    .line 38
    .line 39
    sput-object v4, Lcom/android/systemui/globalactions/GlobalActionsDialogLite$ToggleState;->On:Lcom/android/systemui/globalactions/GlobalActionsDialogLite$ToggleState;

    .line 40
    .line 41
    filled-new-array {v0, v1, v3, v4}, [Lcom/android/systemui/globalactions/GlobalActionsDialogLite$ToggleState;

    .line 42
    .line 43
    .line 44
    move-result-object v0

    .line 45
    sput-object v0, Lcom/android/systemui/globalactions/GlobalActionsDialogLite$ToggleState;->$VALUES:[Lcom/android/systemui/globalactions/GlobalActionsDialogLite$ToggleState;

    .line 46
    .line 47
    return-void
.end method

.method private constructor <init>(Ljava/lang/String;IZ)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(Z)V"
        }
    .end annotation

    .line 1
    invoke-direct {p0, p1, p2}, Ljava/lang/Enum;-><init>(Ljava/lang/String;I)V

    .line 2
    .line 3
    .line 4
    iput-boolean p3, p0, Lcom/android/systemui/globalactions/GlobalActionsDialogLite$ToggleState;->mInTransition:Z

    .line 5
    .line 6
    return-void
.end method

.method public static valueOf(Ljava/lang/String;)Lcom/android/systemui/globalactions/GlobalActionsDialogLite$ToggleState;
    .locals 1

    .line 1
    const-class v0, Lcom/android/systemui/globalactions/GlobalActionsDialogLite$ToggleState;

    .line 2
    .line 3
    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    check-cast p0, Lcom/android/systemui/globalactions/GlobalActionsDialogLite$ToggleState;

    .line 8
    .line 9
    return-object p0
.end method

.method public static values()[Lcom/android/systemui/globalactions/GlobalActionsDialogLite$ToggleState;
    .locals 1

    .line 1
    sget-object v0, Lcom/android/systemui/globalactions/GlobalActionsDialogLite$ToggleState;->$VALUES:[Lcom/android/systemui/globalactions/GlobalActionsDialogLite$ToggleState;

    .line 2
    .line 3
    invoke-virtual {v0}, [Lcom/android/systemui/globalactions/GlobalActionsDialogLite$ToggleState;->clone()Ljava/lang/Object;

    .line 4
    .line 5
    .line 6
    move-result-object v0

    .line 7
    check-cast v0, [Lcom/android/systemui/globalactions/GlobalActionsDialogLite$ToggleState;

    .line 8
    .line 9
    return-object v0
.end method


# virtual methods
.method public final inTransition()Z
    .locals 0

    .line 1
    iget-boolean p0, p0, Lcom/android/systemui/globalactions/GlobalActionsDialogLite$ToggleState;->mInTransition:Z

    .line 2
    .line 3
    return p0
.end method