.class public final enum Lcom/android/wm/shell/bubbles/BubbleLogger$Event;
.super Ljava/lang/Enum;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Lcom/android/internal/logging/UiEventLogger$UiEventEnum;


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "Lcom/android/wm/shell/bubbles/BubbleLogger$Event;",
        ">;",
        "Lcom/android/internal/logging/UiEventLogger$UiEventEnum;"
    }
.end annotation


# static fields
.field public static final synthetic $VALUES:[Lcom/android/wm/shell/bubbles/BubbleLogger$Event;

.field public static final enum BUBBLE_OVERFLOW_ADD_AGED:Lcom/android/wm/shell/bubbles/BubbleLogger$Event;

.field public static final enum BUBBLE_OVERFLOW_ADD_USER_GESTURE:Lcom/android/wm/shell/bubbles/BubbleLogger$Event;

.field public static final enum BUBBLE_OVERFLOW_RECOVER:Lcom/android/wm/shell/bubbles/BubbleLogger$Event;

.field public static final enum BUBBLE_OVERFLOW_REMOVE_BACK_TO_STACK:Lcom/android/wm/shell/bubbles/BubbleLogger$Event;

.field public static final enum BUBBLE_OVERFLOW_REMOVE_BLOCKED:Lcom/android/wm/shell/bubbles/BubbleLogger$Event;

.field public static final enum BUBBLE_OVERFLOW_REMOVE_CANCEL:Lcom/android/wm/shell/bubbles/BubbleLogger$Event;

.field public static final enum BUBBLE_OVERFLOW_REMOVE_GROUP_CANCEL:Lcom/android/wm/shell/bubbles/BubbleLogger$Event;

.field public static final enum BUBBLE_OVERFLOW_REMOVE_MAX_REACHED:Lcom/android/wm/shell/bubbles/BubbleLogger$Event;

.field public static final enum BUBBLE_OVERFLOW_REMOVE_NO_LONGER_BUBBLE:Lcom/android/wm/shell/bubbles/BubbleLogger$Event;

.field public static final enum BUBBLE_OVERFLOW_SELECTED:Lcom/android/wm/shell/bubbles/BubbleLogger$Event;


# instance fields
.field private final mId:I


# direct methods
.method public static constructor <clinit>()V
    .locals 13

    .line 1
    new-instance v0, Lcom/android/wm/shell/bubbles/BubbleLogger$Event;

    .line 2
    .line 3
    const/4 v1, 0x0

    .line 4
    const/16 v2, 0x1e3

    .line 5
    .line 6
    const-string v3, "BUBBLE_OVERFLOW_ADD_USER_GESTURE"

    .line 7
    .line 8
    invoke-direct {v0, v3, v1, v2}, Lcom/android/wm/shell/bubbles/BubbleLogger$Event;-><init>(Ljava/lang/String;II)V

    .line 9
    .line 10
    .line 11
    sput-object v0, Lcom/android/wm/shell/bubbles/BubbleLogger$Event;->BUBBLE_OVERFLOW_ADD_USER_GESTURE:Lcom/android/wm/shell/bubbles/BubbleLogger$Event;

    .line 12
    .line 13
    new-instance v1, Lcom/android/wm/shell/bubbles/BubbleLogger$Event;

    .line 14
    .line 15
    const/4 v2, 0x1

    .line 16
    const/16 v3, 0x1e4

    .line 17
    .line 18
    const-string v4, "BUBBLE_OVERFLOW_ADD_AGED"

    .line 19
    .line 20
    invoke-direct {v1, v4, v2, v3}, Lcom/android/wm/shell/bubbles/BubbleLogger$Event;-><init>(Ljava/lang/String;II)V

    .line 21
    .line 22
    .line 23
    sput-object v1, Lcom/android/wm/shell/bubbles/BubbleLogger$Event;->BUBBLE_OVERFLOW_ADD_AGED:Lcom/android/wm/shell/bubbles/BubbleLogger$Event;

    .line 24
    .line 25
    new-instance v2, Lcom/android/wm/shell/bubbles/BubbleLogger$Event;

    .line 26
    .line 27
    const/4 v3, 0x2

    .line 28
    const/16 v4, 0x1e5

    .line 29
    .line 30
    const-string v5, "BUBBLE_OVERFLOW_REMOVE_MAX_REACHED"

    .line 31
    .line 32
    invoke-direct {v2, v5, v3, v4}, Lcom/android/wm/shell/bubbles/BubbleLogger$Event;-><init>(Ljava/lang/String;II)V

    .line 33
    .line 34
    .line 35
    sput-object v2, Lcom/android/wm/shell/bubbles/BubbleLogger$Event;->BUBBLE_OVERFLOW_REMOVE_MAX_REACHED:Lcom/android/wm/shell/bubbles/BubbleLogger$Event;

    .line 36
    .line 37
    new-instance v3, Lcom/android/wm/shell/bubbles/BubbleLogger$Event;

    .line 38
    .line 39
    const/4 v4, 0x3

    .line 40
    const/16 v5, 0x1e6

    .line 41
    .line 42
    const-string v6, "BUBBLE_OVERFLOW_REMOVE_CANCEL"

    .line 43
    .line 44
    invoke-direct {v3, v6, v4, v5}, Lcom/android/wm/shell/bubbles/BubbleLogger$Event;-><init>(Ljava/lang/String;II)V

    .line 45
    .line 46
    .line 47
    sput-object v3, Lcom/android/wm/shell/bubbles/BubbleLogger$Event;->BUBBLE_OVERFLOW_REMOVE_CANCEL:Lcom/android/wm/shell/bubbles/BubbleLogger$Event;

    .line 48
    .line 49
    new-instance v4, Lcom/android/wm/shell/bubbles/BubbleLogger$Event;

    .line 50
    .line 51
    const/4 v5, 0x4

    .line 52
    const/16 v6, 0x1e7

    .line 53
    .line 54
    const-string v7, "BUBBLE_OVERFLOW_REMOVE_GROUP_CANCEL"

    .line 55
    .line 56
    invoke-direct {v4, v7, v5, v6}, Lcom/android/wm/shell/bubbles/BubbleLogger$Event;-><init>(Ljava/lang/String;II)V

    .line 57
    .line 58
    .line 59
    sput-object v4, Lcom/android/wm/shell/bubbles/BubbleLogger$Event;->BUBBLE_OVERFLOW_REMOVE_GROUP_CANCEL:Lcom/android/wm/shell/bubbles/BubbleLogger$Event;

    .line 60
    .line 61
    new-instance v5, Lcom/android/wm/shell/bubbles/BubbleLogger$Event;

    .line 62
    .line 63
    const/4 v6, 0x5

    .line 64
    const/16 v7, 0x1e8

    .line 65
    .line 66
    const-string v8, "BUBBLE_OVERFLOW_REMOVE_NO_LONGER_BUBBLE"

    .line 67
    .line 68
    invoke-direct {v5, v8, v6, v7}, Lcom/android/wm/shell/bubbles/BubbleLogger$Event;-><init>(Ljava/lang/String;II)V

    .line 69
    .line 70
    .line 71
    sput-object v5, Lcom/android/wm/shell/bubbles/BubbleLogger$Event;->BUBBLE_OVERFLOW_REMOVE_NO_LONGER_BUBBLE:Lcom/android/wm/shell/bubbles/BubbleLogger$Event;

    .line 72
    .line 73
    new-instance v6, Lcom/android/wm/shell/bubbles/BubbleLogger$Event;

    .line 74
    .line 75
    const/4 v7, 0x6

    .line 76
    const/16 v8, 0x1e9

    .line 77
    .line 78
    const-string v9, "BUBBLE_OVERFLOW_REMOVE_BACK_TO_STACK"

    .line 79
    .line 80
    invoke-direct {v6, v9, v7, v8}, Lcom/android/wm/shell/bubbles/BubbleLogger$Event;-><init>(Ljava/lang/String;II)V

    .line 81
    .line 82
    .line 83
    sput-object v6, Lcom/android/wm/shell/bubbles/BubbleLogger$Event;->BUBBLE_OVERFLOW_REMOVE_BACK_TO_STACK:Lcom/android/wm/shell/bubbles/BubbleLogger$Event;

    .line 84
    .line 85
    new-instance v7, Lcom/android/wm/shell/bubbles/BubbleLogger$Event;

    .line 86
    .line 87
    const/4 v8, 0x7

    .line 88
    const/16 v9, 0x1ea

    .line 89
    .line 90
    const-string v10, "BUBBLE_OVERFLOW_REMOVE_BLOCKED"

    .line 91
    .line 92
    invoke-direct {v7, v10, v8, v9}, Lcom/android/wm/shell/bubbles/BubbleLogger$Event;-><init>(Ljava/lang/String;II)V

    .line 93
    .line 94
    .line 95
    sput-object v7, Lcom/android/wm/shell/bubbles/BubbleLogger$Event;->BUBBLE_OVERFLOW_REMOVE_BLOCKED:Lcom/android/wm/shell/bubbles/BubbleLogger$Event;

    .line 96
    .line 97
    new-instance v8, Lcom/android/wm/shell/bubbles/BubbleLogger$Event;

    .line 98
    .line 99
    const/16 v9, 0x8

    .line 100
    .line 101
    const/16 v10, 0x258

    .line 102
    .line 103
    const-string v11, "BUBBLE_OVERFLOW_SELECTED"

    .line 104
    .line 105
    invoke-direct {v8, v11, v9, v10}, Lcom/android/wm/shell/bubbles/BubbleLogger$Event;-><init>(Ljava/lang/String;II)V

    .line 106
    .line 107
    .line 108
    sput-object v8, Lcom/android/wm/shell/bubbles/BubbleLogger$Event;->BUBBLE_OVERFLOW_SELECTED:Lcom/android/wm/shell/bubbles/BubbleLogger$Event;

    .line 109
    .line 110
    new-instance v9, Lcom/android/wm/shell/bubbles/BubbleLogger$Event;

    .line 111
    .line 112
    const/16 v10, 0x9

    .line 113
    .line 114
    const/16 v11, 0x2b3

    .line 115
    .line 116
    const-string v12, "BUBBLE_OVERFLOW_RECOVER"

    .line 117
    .line 118
    invoke-direct {v9, v12, v10, v11}, Lcom/android/wm/shell/bubbles/BubbleLogger$Event;-><init>(Ljava/lang/String;II)V

    .line 119
    .line 120
    .line 121
    sput-object v9, Lcom/android/wm/shell/bubbles/BubbleLogger$Event;->BUBBLE_OVERFLOW_RECOVER:Lcom/android/wm/shell/bubbles/BubbleLogger$Event;

    .line 122
    .line 123
    filled-new-array/range {v0 .. v9}, [Lcom/android/wm/shell/bubbles/BubbleLogger$Event;

    .line 124
    .line 125
    .line 126
    move-result-object v0

    .line 127
    sput-object v0, Lcom/android/wm/shell/bubbles/BubbleLogger$Event;->$VALUES:[Lcom/android/wm/shell/bubbles/BubbleLogger$Event;

    .line 128
    .line 129
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
    iput p3, p0, Lcom/android/wm/shell/bubbles/BubbleLogger$Event;->mId:I

    .line 5
    .line 6
    return-void
.end method

.method public static valueOf(Ljava/lang/String;)Lcom/android/wm/shell/bubbles/BubbleLogger$Event;
    .locals 1

    .line 1
    const-class v0, Lcom/android/wm/shell/bubbles/BubbleLogger$Event;

    .line 2
    .line 3
    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    check-cast p0, Lcom/android/wm/shell/bubbles/BubbleLogger$Event;

    .line 8
    .line 9
    return-object p0
.end method

.method public static values()[Lcom/android/wm/shell/bubbles/BubbleLogger$Event;
    .locals 1

    .line 1
    sget-object v0, Lcom/android/wm/shell/bubbles/BubbleLogger$Event;->$VALUES:[Lcom/android/wm/shell/bubbles/BubbleLogger$Event;

    .line 2
    .line 3
    invoke-virtual {v0}, [Lcom/android/wm/shell/bubbles/BubbleLogger$Event;->clone()Ljava/lang/Object;

    .line 4
    .line 5
    .line 6
    move-result-object v0

    .line 7
    check-cast v0, [Lcom/android/wm/shell/bubbles/BubbleLogger$Event;

    .line 8
    .line 9
    return-object v0
.end method


# virtual methods
.method public final getId()I
    .locals 0

    .line 1
    iget p0, p0, Lcom/android/wm/shell/bubbles/BubbleLogger$Event;->mId:I

    .line 2
    .line 3
    return p0
.end method
