.class public final enum Lcom/android/app/motiontool/MotionToolsRequest$TypeCase;
.super Ljava/lang/Enum;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "Lcom/android/app/motiontool/MotionToolsRequest$TypeCase;",
        ">;"
    }
.end annotation


# static fields
.field public static final synthetic $VALUES:[Lcom/android/app/motiontool/MotionToolsRequest$TypeCase;

.field public static final enum BEGIN_TRACE:Lcom/android/app/motiontool/MotionToolsRequest$TypeCase;

.field public static final enum END_TRACE:Lcom/android/app/motiontool/MotionToolsRequest$TypeCase;

.field public static final enum HANDSHAKE:Lcom/android/app/motiontool/MotionToolsRequest$TypeCase;

.field public static final enum POLL_TRACE:Lcom/android/app/motiontool/MotionToolsRequest$TypeCase;

.field public static final enum TYPE_NOT_SET:Lcom/android/app/motiontool/MotionToolsRequest$TypeCase;


# instance fields
.field private final value:I


# direct methods
.method public static constructor <clinit>()V
    .locals 8

    .line 1
    new-instance v0, Lcom/android/app/motiontool/MotionToolsRequest$TypeCase;

    .line 2
    .line 3
    const-string v1, "HANDSHAKE"

    .line 4
    .line 5
    const/4 v2, 0x0

    .line 6
    const/4 v3, 0x1

    .line 7
    invoke-direct {v0, v1, v2, v3}, Lcom/android/app/motiontool/MotionToolsRequest$TypeCase;-><init>(Ljava/lang/String;II)V

    .line 8
    .line 9
    .line 10
    sput-object v0, Lcom/android/app/motiontool/MotionToolsRequest$TypeCase;->HANDSHAKE:Lcom/android/app/motiontool/MotionToolsRequest$TypeCase;

    .line 11
    .line 12
    new-instance v1, Lcom/android/app/motiontool/MotionToolsRequest$TypeCase;

    .line 13
    .line 14
    const-string v4, "BEGIN_TRACE"

    .line 15
    .line 16
    const/4 v5, 0x2

    .line 17
    invoke-direct {v1, v4, v3, v5}, Lcom/android/app/motiontool/MotionToolsRequest$TypeCase;-><init>(Ljava/lang/String;II)V

    .line 18
    .line 19
    .line 20
    sput-object v1, Lcom/android/app/motiontool/MotionToolsRequest$TypeCase;->BEGIN_TRACE:Lcom/android/app/motiontool/MotionToolsRequest$TypeCase;

    .line 21
    .line 22
    new-instance v3, Lcom/android/app/motiontool/MotionToolsRequest$TypeCase;

    .line 23
    .line 24
    const-string v4, "END_TRACE"

    .line 25
    .line 26
    const/4 v6, 0x3

    .line 27
    invoke-direct {v3, v4, v5, v6}, Lcom/android/app/motiontool/MotionToolsRequest$TypeCase;-><init>(Ljava/lang/String;II)V

    .line 28
    .line 29
    .line 30
    sput-object v3, Lcom/android/app/motiontool/MotionToolsRequest$TypeCase;->END_TRACE:Lcom/android/app/motiontool/MotionToolsRequest$TypeCase;

    .line 31
    .line 32
    new-instance v4, Lcom/android/app/motiontool/MotionToolsRequest$TypeCase;

    .line 33
    .line 34
    const-string v5, "POLL_TRACE"

    .line 35
    .line 36
    const/4 v7, 0x4

    .line 37
    invoke-direct {v4, v5, v6, v7}, Lcom/android/app/motiontool/MotionToolsRequest$TypeCase;-><init>(Ljava/lang/String;II)V

    .line 38
    .line 39
    .line 40
    sput-object v4, Lcom/android/app/motiontool/MotionToolsRequest$TypeCase;->POLL_TRACE:Lcom/android/app/motiontool/MotionToolsRequest$TypeCase;

    .line 41
    .line 42
    new-instance v5, Lcom/android/app/motiontool/MotionToolsRequest$TypeCase;

    .line 43
    .line 44
    const-string v6, "TYPE_NOT_SET"

    .line 45
    .line 46
    invoke-direct {v5, v6, v7, v2}, Lcom/android/app/motiontool/MotionToolsRequest$TypeCase;-><init>(Ljava/lang/String;II)V

    .line 47
    .line 48
    .line 49
    sput-object v5, Lcom/android/app/motiontool/MotionToolsRequest$TypeCase;->TYPE_NOT_SET:Lcom/android/app/motiontool/MotionToolsRequest$TypeCase;

    .line 50
    .line 51
    filled-new-array {v0, v1, v3, v4, v5}, [Lcom/android/app/motiontool/MotionToolsRequest$TypeCase;

    .line 52
    .line 53
    .line 54
    move-result-object v0

    .line 55
    sput-object v0, Lcom/android/app/motiontool/MotionToolsRequest$TypeCase;->$VALUES:[Lcom/android/app/motiontool/MotionToolsRequest$TypeCase;

    .line 56
    .line 57
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
    iput p3, p0, Lcom/android/app/motiontool/MotionToolsRequest$TypeCase;->value:I

    .line 5
    .line 6
    return-void
.end method

.method public static valueOf(Ljava/lang/String;)Lcom/android/app/motiontool/MotionToolsRequest$TypeCase;
    .locals 1

    .line 1
    const-class v0, Lcom/android/app/motiontool/MotionToolsRequest$TypeCase;

    .line 2
    .line 3
    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    check-cast p0, Lcom/android/app/motiontool/MotionToolsRequest$TypeCase;

    .line 8
    .line 9
    return-object p0
.end method

.method public static values()[Lcom/android/app/motiontool/MotionToolsRequest$TypeCase;
    .locals 1

    .line 1
    sget-object v0, Lcom/android/app/motiontool/MotionToolsRequest$TypeCase;->$VALUES:[Lcom/android/app/motiontool/MotionToolsRequest$TypeCase;

    .line 2
    .line 3
    invoke-virtual {v0}, [Lcom/android/app/motiontool/MotionToolsRequest$TypeCase;->clone()Ljava/lang/Object;

    .line 4
    .line 5
    .line 6
    move-result-object v0

    .line 7
    check-cast v0, [Lcom/android/app/motiontool/MotionToolsRequest$TypeCase;

    .line 8
    .line 9
    return-object v0
.end method


# virtual methods
.method public final getNumber()I
    .locals 0

    .line 1
    iget p0, p0, Lcom/android/app/motiontool/MotionToolsRequest$TypeCase;->value:I

    .line 2
    .line 3
    return p0
.end method