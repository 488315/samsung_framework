.class final enum Lcom/android/systemui/statusbar/policy/HeadsUpManager$NotificationPeekEvent;
.super Ljava/lang/Enum;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/android/internal/logging/UiEventLogger$UiEventEnum;


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "Lcom/android/systemui/statusbar/policy/HeadsUpManager$NotificationPeekEvent;",
        ">;",
        "Lcom/android/internal/logging/UiEventLogger$UiEventEnum;"
    }
.end annotation


# static fields
.field public static final synthetic $VALUES:[Lcom/android/systemui/statusbar/policy/HeadsUpManager$NotificationPeekEvent;

.field public static final enum NOTIFICATION_PEEK:Lcom/android/systemui/statusbar/policy/HeadsUpManager$NotificationPeekEvent;


# instance fields
.field private final mId:I


# direct methods
.method public static constructor <clinit>()V
    .locals 4

    .line 1
    new-instance v0, Lcom/android/systemui/statusbar/policy/HeadsUpManager$NotificationPeekEvent;

    .line 2
    .line 3
    const/4 v1, 0x0

    .line 4
    const/16 v2, 0x321

    .line 5
    .line 6
    const-string v3, "NOTIFICATION_PEEK"

    .line 7
    .line 8
    invoke-direct {v0, v3, v1, v2}, Lcom/android/systemui/statusbar/policy/HeadsUpManager$NotificationPeekEvent;-><init>(Ljava/lang/String;II)V

    .line 9
    .line 10
    .line 11
    sput-object v0, Lcom/android/systemui/statusbar/policy/HeadsUpManager$NotificationPeekEvent;->NOTIFICATION_PEEK:Lcom/android/systemui/statusbar/policy/HeadsUpManager$NotificationPeekEvent;

    .line 12
    .line 13
    filled-new-array {v0}, [Lcom/android/systemui/statusbar/policy/HeadsUpManager$NotificationPeekEvent;

    .line 14
    .line 15
    .line 16
    move-result-object v0

    .line 17
    sput-object v0, Lcom/android/systemui/statusbar/policy/HeadsUpManager$NotificationPeekEvent;->$VALUES:[Lcom/android/systemui/statusbar/policy/HeadsUpManager$NotificationPeekEvent;

    .line 18
    .line 19
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
    iput p3, p0, Lcom/android/systemui/statusbar/policy/HeadsUpManager$NotificationPeekEvent;->mId:I

    .line 5
    .line 6
    return-void
.end method

.method public static valueOf(Ljava/lang/String;)Lcom/android/systemui/statusbar/policy/HeadsUpManager$NotificationPeekEvent;
    .locals 1

    .line 1
    const-class v0, Lcom/android/systemui/statusbar/policy/HeadsUpManager$NotificationPeekEvent;

    .line 2
    .line 3
    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    check-cast p0, Lcom/android/systemui/statusbar/policy/HeadsUpManager$NotificationPeekEvent;

    .line 8
    .line 9
    return-object p0
.end method

.method public static values()[Lcom/android/systemui/statusbar/policy/HeadsUpManager$NotificationPeekEvent;
    .locals 1

    .line 1
    sget-object v0, Lcom/android/systemui/statusbar/policy/HeadsUpManager$NotificationPeekEvent;->$VALUES:[Lcom/android/systemui/statusbar/policy/HeadsUpManager$NotificationPeekEvent;

    .line 2
    .line 3
    invoke-virtual {v0}, [Lcom/android/systemui/statusbar/policy/HeadsUpManager$NotificationPeekEvent;->clone()Ljava/lang/Object;

    .line 4
    .line 5
    .line 6
    move-result-object v0

    .line 7
    check-cast v0, [Lcom/android/systemui/statusbar/policy/HeadsUpManager$NotificationPeekEvent;

    .line 8
    .line 9
    return-object v0
.end method


# virtual methods
.method public final getId()I
    .locals 0

    .line 1
    iget p0, p0, Lcom/android/systemui/statusbar/policy/HeadsUpManager$NotificationPeekEvent;->mId:I

    .line 2
    .line 3
    return p0
.end method
