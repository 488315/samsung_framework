.class public final enum Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;
.super Ljava/lang/Enum;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x4019
    name = "ExecType"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;",
        ">;"
    }
.end annotation


# static fields
.field public static final synthetic $VALUES:[Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;

.field public static final enum BFLOAT16:Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;

.field public static final enum FLOAT16:Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;

.field public static final enum FLOAT32:Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;

.field public static final enum QASYMM16:Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;

.field public static final enum QASYMM8:Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;


# direct methods
.method public static synthetic $values()[Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;
    .locals 5

    .line 1
    sget-object v0, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;->FLOAT32:Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;

    .line 2
    .line 3
    sget-object v1, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;->FLOAT16:Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;

    .line 4
    .line 5
    sget-object v2, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;->QASYMM16:Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;

    .line 6
    .line 7
    sget-object v3, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;->QASYMM8:Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;

    .line 8
    .line 9
    sget-object v4, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;->BFLOAT16:Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;

    .line 10
    .line 11
    filled-new-array {v0, v1, v2, v3, v4}, [Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;

    .line 12
    .line 13
    .line 14
    move-result-object v0

    .line 15
    return-object v0
.end method

.method public static constructor <clinit>()V
    .locals 3

    .line 1
    new-instance v0, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;

    .line 2
    .line 3
    const-string v1, "FLOAT32"

    .line 4
    .line 5
    const/4 v2, 0x0

    .line 6
    invoke-direct {v0, v1, v2}, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;-><init>(Ljava/lang/String;I)V

    .line 7
    .line 8
    .line 9
    sput-object v0, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;->FLOAT32:Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;

    .line 10
    .line 11
    new-instance v0, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;

    .line 12
    .line 13
    const-string v1, "FLOAT16"

    .line 14
    .line 15
    const/4 v2, 0x1

    .line 16
    invoke-direct {v0, v1, v2}, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;-><init>(Ljava/lang/String;I)V

    .line 17
    .line 18
    .line 19
    sput-object v0, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;->FLOAT16:Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;

    .line 20
    .line 21
    new-instance v0, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;

    .line 22
    .line 23
    const-string v1, "QASYMM16"

    .line 24
    .line 25
    const/4 v2, 0x2

    .line 26
    invoke-direct {v0, v1, v2}, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;-><init>(Ljava/lang/String;I)V

    .line 27
    .line 28
    .line 29
    sput-object v0, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;->QASYMM16:Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;

    .line 30
    .line 31
    new-instance v0, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;

    .line 32
    .line 33
    const-string v1, "QASYMM8"

    .line 34
    .line 35
    const/4 v2, 0x3

    .line 36
    invoke-direct {v0, v1, v2}, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;-><init>(Ljava/lang/String;I)V

    .line 37
    .line 38
    .line 39
    sput-object v0, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;->QASYMM8:Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;

    .line 40
    .line 41
    new-instance v0, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;

    .line 42
    .line 43
    const-string v1, "BFLOAT16"

    .line 44
    .line 45
    const/4 v2, 0x4

    .line 46
    invoke-direct {v0, v1, v2}, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;-><init>(Ljava/lang/String;I)V

    .line 47
    .line 48
    .line 49
    sput-object v0, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;->BFLOAT16:Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;

    .line 50
    .line 51
    invoke-static {}, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;->$values()[Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;

    .line 52
    .line 53
    .line 54
    move-result-object v0

    .line 55
    sput-object v0, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;->$VALUES:[Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;

    .line 56
    .line 57
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

.method public static valueOf(Ljava/lang/String;)Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;
    .locals 1

    .line 1
    const-class v0, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;

    .line 2
    .line 3
    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    check-cast p0, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;

    .line 8
    .line 9
    return-object p0
.end method

.method public static values()[Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;
    .locals 1

    .line 1
    sget-object v0, Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;->$VALUES:[Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;

    .line 2
    .line 3
    invoke-virtual {v0}, [Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;->clone()Ljava/lang/Object;

    .line 4
    .line 5
    .line 6
    move-result-object v0

    .line 7
    check-cast v0, [Lcom/samsung/android/knox/ex/knoxAI/KnoxAiSession$ExecType;

    .line 8
    .line 9
    return-object v0
.end method


# virtual methods
.method public final getValue()I
    .locals 0

    .line 1
    invoke-virtual {p0}, Ljava/lang/Enum;->ordinal()I

    .line 2
    .line 3
    .line 4
    move-result p0

    .line 5
    return p0
.end method